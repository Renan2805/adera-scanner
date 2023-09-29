package com.adera.extensions;

import java.sql.SQLException;

public class MySQLExtension {
    public static void handleException(SQLException e) {
        System.err.printf("""
                    Erro MySQL
                    Codigo: %d
                    Mensagem: %s
                    Causa: %s%n""", e.getErrorCode(), e.getMessage(), e.getCause());
    }
}
