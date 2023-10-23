package com.adera.database;

import com.adera.entities.ComponentEntity;
import com.adera.enums.ComponentTypeEnum;
import com.adera.extensions.MySQLExtension;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class ComponentDatabase {
    private final Connection conn ;

    public ComponentDatabase(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<ComponentEntity> getComponentsByMachineId(UUID idMachine) throws SQLException{
        String query = "SELECT * FROM maquinacomponente join tipocomponente on maquinacomponente.fktipocomponente = tipocomponente.id join unidademedida on tipocomponente.fkunidademedida = unidademedida.id where maquinacomponente.fkMaquina = ?";
        PreparedStatement statement = conn.prepareStatement(query);

        try {
            statement.setString(1, idMachine.toString());
            statement.execute();

            ResultSet result = statement.getResultSet();

            ArrayList<ComponentEntity> list = new ArrayList<>();

            while(result.next()) {
                ComponentEntity component = new ComponentEntity(
                        UUID.fromString(result.getString(1)),
                        result.getString(2),
                        result.getString(3),
                        result.getDouble(4),
                        UUID.fromString(result.getString(5)),
                        ComponentTypeEnum.valueOf(result.getString("tipocomponente.nome"))
                );
                list.add(component);
            }

            return list;
        } catch (SQLException e) {
            MySQLExtension.handleException(e);
            return new ArrayList<>();
        }

    }
    public ArrayList<ComponentEntity> getComponentByMachineId(UUID machineId) throws SQLException {
        String query = "SELECT * FROM maquinacomponente WHERE fkMaquina = ?";
        PreparedStatement statement = this.conn.prepareStatement(query);

        try {
            statement.execute();

            ResultSet result = statement.getResultSet();

            ArrayList<ComponentEntity> list = new ArrayList<>();
            while(result.next()) {
                ComponentEntity component = new ComponentEntity(
                        UUID.fromString(result.getString(1)),
                        result.getString(2),
                        result.getString(3),
                        result.getDouble(4),
                        UUID.fromString(result.getString(5)),
                        ComponentTypeEnum.valueOf(result.getString(5))
                );
                list.add(component);
            }

            return list;
        } catch(SQLException e) {
            MySQLExtension.handleException(e);
            return null;
        }
    }

    public void insertOne(ComponentEntity component) throws SQLException {
        String query = "INSERT INTO maquinacomponente VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = this.conn.prepareStatement(query);

        try {
            statement.setString(1, component.getId().toString());
            statement.setString(2, component.getModel());
            statement.setString(3, component.getDescription());
            statement.setDouble(4, component.getCapacity());
            statement.setString(5, component.getIdMachine().toString());
            statement.setInt(6, component.getType().getId());

            statement.execute();

            ResultSet result = statement.getResultSet();
        } catch(SQLException e) { MySQLExtension.handleException(e); }
    }
}
