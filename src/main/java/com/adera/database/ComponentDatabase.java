package com.adera.database;

import com.adera.entities.ComponentEntity;
import com.adera.extensions.MySQLExtension;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

public class ComponentDatabase {
    private final Connection conn ;

    public ComponentDatabase(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<ComponentEntity> getComponentsByMachineId(UUID idMachine) throws SQLException{
        String query = "SELECT * FROM maquinacomponente where fkMaquina = ? " +
                "join tipocomponente on maquinacomponente.fktipocomponente = tipocomponente.id" +
                "join unidademedida on tipocomponente.fkunidademedida = unidademedida.id";
        Statement statement = conn.createStatement();

        try {
            ResultSet result = statement.executeQuery(query);

            ArrayList<ComponentEntity> list = new ArrayList<>();

            while(result.next()) {
                ComponentEntity component = new ComponentEntity();
                component.setId(UUID.fromString(result.getString(1)));
                component.setModel(result.getString(2));
                component.setDescription(result.getString(3));
                component.setIdMachine(UUID.fromString(result.getString(4)));
                component.setType(result.getInt(5));
                list.add(component);
            }

            return list;
        } catch (SQLException e) {
            MySQLExtension.handleException(e);
            return new ArrayList<>();
        }
    }
}
