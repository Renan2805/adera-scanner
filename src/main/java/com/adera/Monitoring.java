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

public class Monitoring {


    public static void start(EstablishmentEntity establishment) {
        Machine thisMachine = getHardwareInfo();

        System.out.println(thisMachine);
    }

    private static Machine getHardwareInfo() {

        Looca looca = new Looca();

        Machine machine = new Machine();

        Sistema sys = looca.getSistema();
        machine.setOs(sys.getSistemaOperacional());
        machine.setVendor(sys.getFabricante());
        machine.setArchitecture(sys.getArquitetura());
        machine.setMacAddress(looca.getRede().getGrupoDeInterfaces().getInterfaces().get(0).getEnderecoMac());

        Processador cpu = looca.getProcessador();
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

        DiscoGrupo disks = looca.getGrupoDeDiscos();
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
}
