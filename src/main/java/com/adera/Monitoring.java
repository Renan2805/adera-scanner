package com.adera;

import com.adera.commonTypes.Component;
import com.adera.commonTypes.Machine;
import com.adera.entities.EstablishmentEntity;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.sistema.Sistema;
import com.github.britooo.looca.api.util.Conversor;

import java.io.File;
import java.util.UUID;

public class Monitoring {
    private Looca _looca;

    public static void setup(EstablishmentEntity establishment) {
        Monitoring monitor = new Monitoring();
        monitor._looca = new Looca();

        Machine thisMachine = monitor.getHardwareInfo(establishment.getId());

        System.out.println(establishment);
        System.out.println(thisMachine);
    }

    private Machine getHardwareInfo(UUID establishmentId) {
        assert this._looca != null;

        Machine machine = new Machine();

        Sistema sys = this._looca.getSistema();
        machine.setOs(sys.getSistemaOperacional());
        machine.setVendor(sys.getFabricante());
        machine.setArchitecture(sys.getArquitetura());
        machine.setMacAddress(this._looca.getRede().getGrupoDeInterfaces().getInterfaces().get(0).getEnderecoMac());
        machine.setEstablishmentId(establishmentId);

        Processador cpu = this._looca.getProcessador();
        Component cpuComponent = new Component();
        cpuComponent.setType("cpu");
        cpuComponent.setMetricUnit("%");
        cpuComponent.setModel(cpu.getNome());

        String cpuDescription = String.format("%s, Núcleos: %d, Threads: %d", cpu.getMicroarquitetura(), cpu.getNumeroCpusFisicas(), cpu.getNumeroCpusLogicas());
        cpuComponent.setDescription(cpuDescription);
        machine.getComponents().add(cpuComponent);

        Memoria mem = new Memoria();
        Component memoryComponent = new Component();
        memoryComponent.setType("memoria");
        memoryComponent.setMetricUnit("byte");
        memoryComponent.setModel(Conversor.formatarBytes(mem.getTotal()));
        machine.getComponents().add(memoryComponent);

        DiscoGrupo disks = this._looca.getGrupoDeDiscos();
        for (Disco disk : disks.getDiscos()) {
            Component diskComponent = new Component();
            diskComponent.setType("disco");
            diskComponent.setMetricUnit("byte");
            diskComponent.setModel(disk.getModelo());
            diskComponent.setDescription(Conversor.formatarBytes(disk.getTamanho()));
            machine.getComponents().add(diskComponent);
        }
        return machine;
    }

    private void syncDatabase(Machine machine) {

    }
}
