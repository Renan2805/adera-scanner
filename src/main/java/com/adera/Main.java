package com.adera;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.rede.Rede;
import com.github.britooo.looca.api.group.sistema.Sistema;
import com.github.britooo.looca.api.util.Conversor;

import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws SQLException {
        Looca looca = new Looca();

        Sistema sys = looca.getSistema();
        Rede rede = looca.getRede();
        Processador cpu = looca.getProcessador();
        Memoria mem = looca.getMemoria();

        var intefaces = rede.getGrupoDeInterfaces().getInterfaces().get(0).getEnderecoIpv4();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.printf("""
                        IP: %s
                        Uso do processador: %.2f%%
                        Uso da memória: %s%n""", rede.getGrupoDeInterfaces().getInterfaces(), cpu.getUso(), Conversor.formatarBytes(mem.getEmUso()));
            }
        };

        Timer timer = new Timer("Timer");

        timer.schedule(task, 0, 1000);
    }
}