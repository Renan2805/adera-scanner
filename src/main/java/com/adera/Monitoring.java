package com.adera;

import com.adera.commonTypes.Component;
import com.adera.commonTypes.Establishment;
import com.adera.commonTypes.Machine;
import com.adera.database.ComponentDatabase;
import com.adera.database.ConnectionMySQL;
import com.adera.database.MachineDatabase;
import com.adera.entities.ComponentEntity;
import com.adera.entities.EstablishmentEntity;
import com.adera.entities.MachineEntity;
import com.adera.entities.MetricEntity;
import com.adera.enums.ComponentTypeEnum;
import com.adera.extensions.MySQLExtension;
import com.adera.mappers.ComponentMapper;
import com.adera.mappers.EstablishmentMapper;
import com.adera.mappers.MachineMapper;
import com.adera.repositories.ComponentRepository;
import com.adera.repositories.MachineRepository;
import com.adera.repositories.MetricRepository;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.sistema.Sistema;
import com.github.britooo.looca.api.util.Conversor;
import jdk.jshell.spi.ExecutionControl;
import lombok.SneakyThrows;
import org.checkerframework.checker.units.qual.A;

import java.sql.SQLException;
import java.sql.Date;
import java.util.*;

public class Monitoring {
    private Looca _looca;

    private Machine machine;
    private Establishment establishment;

    public static void setup(EstablishmentEntity establishment) {
        Monitoring monitor = new Monitoring();
        monitor._looca = new Looca();

        monitor.machine = monitor.buildMachine(establishment.getId());
        monitor.establishment = EstablishmentMapper.toEstablishment(establishment);

        MachineEntity isNewMachine = monitor.checkIfNewMachine();
        System.out.println(isNewMachine.getId());
        if(isNewMachine != null) {
            ComponentRepository repository = new ComponentRepository(new HashMap<>());
            ComponentDatabase database = new ComponentDatabase(ConnectionMySQL.getConnection());

            monitor.machine = MachineMapper.toMachine(isNewMachine, null);
            try {
                var entityList = database.getComponentsByMachineId(monitor.machine.getId());

                var componentList = new ArrayList<Component>();

                for(var entity : entityList) {
                    String unit = "";
                    if(entity.getType() == ComponentTypeEnum.CPU) {
                        unit = "porcentagem";
                    } else {
                        unit = "byte";
                    }
                    componentList.add(ComponentMapper.toComponent(entity, unit));
                }

                monitor.machine.setComponents(componentList);
            } catch(SQLException e) {
                MySQLExtension.handleException(e);
            }


//            ArrayList<ComponentEntity> machineComponentsInDatabase = monitor.findMachineComponents();
//
//            if(machineComponentsInDatabase == null) {
//                for(Component component : monitor.machine.getComponents()) {
//                    component.setId(UUID.randomUUID());
//                    ComponentEntity entity = ComponentMapper.toComponentEntity(component, monitor.machine.getId());
//                    repository.registerNew(entity);
//                }
//            }

            repository.commit();
        } else {
            monitor.machine.setId(UUID.randomUUID());
            MachineRepository machineRepository = new MachineRepository(new HashMap<>());
            machineRepository.registerNew(MachineMapper.toMachineEntity(monitor.machine));
            machineRepository.commit();

            ComponentRepository componentRepository = new ComponentRepository(new HashMap<>());
            ArrayList<Component> componentList = new ArrayList<>();
            for(int i = 0; i < monitor.machine.getComponents().size(); i++) {
                var component = monitor.machine.getComponents().get(i);
                component.setId(UUID.randomUUID());
                componentRepository.registerNew(ComponentMapper.toComponentEntity(component, monitor.machine.getId()));
                componentList.add(component);
            }
            monitor.machine.setComponents(componentList);
            componentRepository.commit();
        }

        System.out.println(establishment);
        System.out.println(monitor.machine);

        monitor.start();
    }

    private void start() {
        MetricRepository repository = new MetricRepository();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                var components = machine.getComponents();

                for(var component : components) {
                    System.out.println(component);
                    if(component.getType() == ComponentTypeEnum.CPU) {
                        repository.registerNew(getCpuMetric(component.getId()));
                    }
                }

                repository.commit();
            }
        };

        Timer timer = new Timer();

        timer.schedule(task, new java.util.Date(), 1000);
    }

    private MetricEntity getCpuMetric(UUID componentId) {
        return new MetricEntity(
                UUID.randomUUID(),
                new java.sql.Date(new java.util.Date().getTime()),
                this._looca.getProcessador().getUso().intValue(),
                componentId
        );
    }

    private Machine buildMachine(UUID establishmentId) {
        assert this._looca != null;

        Machine machine = new Machine();

        ArrayList<Component> components = new ArrayList<>();

        Sistema sys = this._looca.getSistema();
        machine.setOs(sys.getSistemaOperacional());
        machine.setVendor(sys.getFabricante());
        machine.setArchitecture(sys.getArquitetura());
        machine.setMacAddress(this._looca.getRede().getGrupoDeInterfaces().getInterfaces().get(0).getEnderecoMac());
        machine.setEstablishmentId(establishmentId);

        Processador cpu = this._looca.getProcessador();
        String cpuDescription = String.format("%s, Núcleos: %d, Threads: %d", cpu.getMicroarquitetura(), cpu.getNumeroCpusFisicas(), cpu.getNumeroCpusLogicas());

        Component cpuComponent = new Component(
                null,
                cpu.getNome(),
                cpuDescription,
                ComponentTypeEnum.CPU,
                "%"
        );
        components.add(cpuComponent);

        Memoria mem = new Memoria();
        Component memoryComponent = new Component();
        memoryComponent.setType(ComponentTypeEnum.MEMORY);
        memoryComponent.setMetricUnit("byte");
        memoryComponent.setModel(Conversor.formatarBytes(mem.getTotal()));
        memoryComponent.setDescription(Conversor.formatarBytes(mem.getTotal()));
        components.add(memoryComponent);

        DiscoGrupo disks = this._looca.getGrupoDeDiscos();
        for (Disco disk : disks.getDiscos()) {
            Component diskComponent = new Component();
            diskComponent.setType(ComponentTypeEnum.DISK);
            diskComponent.setMetricUnit("byte");
            diskComponent.setModel(disk.getModelo());
            diskComponent.setDescription(Conversor.formatarBytes(disk.getTamanho()));
            components.add(diskComponent);
        }

        machine.setComponents(components);
        return machine;
    }

    private MachineEntity checkIfNewMachine() {
        try {
            MachineEntity existing = new MachineDatabase().getMachineByMacAddress(this.machine.getMacAddress());
            System.out.println(existing);
            return existing;
        } catch(SQLException e) {
            MySQLExtension.handleException(e);
            return null;
        }
    }

    @SneakyThrows
    private ArrayList<ComponentEntity> findMachineComponents() {
        throw new ExecutionControl.NotImplementedException("");
    }
}
