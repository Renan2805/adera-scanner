package com.adera.repositories;

import com.adera.commonTypes.Machine;
import com.adera.database.ConnectionMySQL;
import com.adera.extensions.MySQLExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MachineRepository {
    private static final Connection conn = ConnectionMySQL.getConnection();

    public static Machine getMachineByMacAddress(String macAddress) throws SQLException {
        assert conn != null;
        String query = "select * from maquina where enderecoMac = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        try {
            statement.setString(1, macAddress);
            statement.execute();
            ResultSet result = statement.getResultSet();

            Machine machine = new Machine();
            while(result.next()) {
                machine.setId(UUID.fromString(result.getString(1)));
                machine.setOs(result.getString(2));
                machine.setArchitecture(result.getInt(3));
            }
            return machine;
        } catch (SQLException e) {
            MySQLExtension.handleException(e);
            return null;
        }
    }

    public static void insertOne(Machine machine) throws SQLException {
        assert conn != null;
        String query = "INSERT INTO maquina VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(query);
        try {
            statement.setString(1, machine.getId().toString());
            statement.setString(2, machine.getOs());
            statement.setString(3, machine.getArchitecture().toString());
            statement.setString(4, machine.getMacAddress());
            statement.setString(5, machine.getEstablishmentId().toString());
            statement.execute();

        } catch (SQLException e) {
            MySQLExtension.handleException(e);
            return;
        }
    }
}
