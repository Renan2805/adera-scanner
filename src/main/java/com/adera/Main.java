package com.adera;

import com.adera.commonTypes.Config;
import com.adera.entities.EstablishmentEntity;
import com.adera.entities.UserEntity;
import com.adera.repositories.EstablishmentRepository;
import com.adera.repositories.UserRepository;
import com.github.britooo.looca.api.core.Looca;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    private static UserEntity user = null;
    private static EstablishmentEntity establishment = null;
    private static boolean logged = false;

    public static void main(String[] args) throws SQLException, FileNotFoundException {
        //        EstablishmentEntity _establishment = null;
//        if(args.length != 0) {
//            String ecCode = args[0];
//            _establishment = EstablishmentRepository.getByEcCode(ecCode);
//        } else {
//            AskCodeGui askGui = new AskCodeGui();
//            askGui.launch();
//        }
//        System.out.println(_establishment);
        ArrayList<String> errList = new ArrayList<>();

        do {
            Config cfg = tryReadCfgFile();

            if (cfg == null) {
                createCfgFile();
                cfg = tryReadCfgFile();
            }

            if (errList.contains("notfound")) {
                System.err.println("\nEmail ou Senha inválidos\n");
            }

            errList.clear();
            if (user == null && cfg != null && cfg.getUserId() != null) {
                user = UserRepository.getOneById(cfg.getUserId());
                if (user != null) {
                    writeToCfgFile(user.getId().toString());
                    establishment = EstablishmentRepository.getOneById(user.getEstablishmentId().toString());
                    logged = true;
                }
            } else {
                user = requestEmailAndPassword();

                if (user == null) {
                    errList.add("Usuário não encontrado!");
                    System.out.println("\n\nEmail ou senha inválidos!\n\n");
                } else {
                    writeToCfgFile(user.getId().toString());
                    establishment = EstablishmentRepository.getOneById(user.getEstablishmentId().toString());
                    logged = true;
                }
            }

        } while (!logged);

        showMenu();
    }

    public static void showMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== MENU ===");
            System.out.println("1. Fazer Login");
            System.out.println("2. Ver o que você pode monitorar");
            System.out.println("3. Sobre nós");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    // Opção de fazer login
                    try {
                        user = requestEmailAndPassword();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    if (user != null) {
                        establishment = EstablishmentRepository.getOneById(user.getEstablishmentId().toString());
                        logged = true;
                        System.out.println("Login realizado com sucesso!");
                    } else {
                        System.out.println("Email ou senha inválidos.");
                    }
                    break;
                case 2:
                    // Opção de mostrar o que pode ser monitorado
                    mostrarOpcoesMonitoramento();
                    break;
                case 3:
                    // Opção "Sobre nós"
                    mostrarSobreNos();
                    break;
                case 0:
                    // Opção de sair
                    System.out.println("Saindo...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, tente novamente.");
                    break;
            }

            System.out.println();
        }
    }

    public static UserEntity requestEmailAndPassword() throws SQLException {
        Scanner in = new Scanner(System.in);
        System.out.print("Email: ");
        String email = in.next();

        System.out.print("Senha: ");
        String password = in.next();

        return UserRepository.getOneByEmailAndPassword(email, password);
    }

    public static Config tryReadCfgFile() throws FileNotFoundException {
        try {
            File cfgFile = new File("config.txt");
            Scanner myReader = new Scanner(cfgFile);
            Config cfg = new Config();
            while (myReader.hasNextLine()) {
                cfg.setUserId(myReader.nextLine());
            }
            return cfg;
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static void createCfgFile() {
        try {
            File myObj = new File("config.txt");
            myObj.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void writeToCfgFile(String userId) {
        try {
            FileWriter myWriter = new FileWriter("config.txt");
            myWriter.write(userId);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void mostrarOpcoesMonitoramento() {
        System.out.println("=== O que você pode monitorar ===");
        System.out.println("- CPU");
        System.out.println("- Memória");
        System.out.println("- Disco");
        System.out.println("- Rede");
        System.out.println();
    }

    public static void mostrarSobreNos() {
        System.out.println("=== Sobre nós ===");
        System.out.println("Nossa inovação oferece um Dashboard de Controle Avançado que permite aos gerentes monitorar em tempo real o desempenho dos totens. Eles podem agendar reinicializações preventivas e executar medidas imediatas para evitar problemas e solucionar o mau funcionamento do totem através do Adera Scanner.");
        System.out.println("Também recebem alertas imediatos caso ocorra qualquer instabilidade, e a integração eficiente entre nossa aplicação e o Dashboard facilita a tomada de decisões informadas, proporcionando uma experiência de compra mais fluida e confiável para os clientes.");
        System.out.println();
    }
}