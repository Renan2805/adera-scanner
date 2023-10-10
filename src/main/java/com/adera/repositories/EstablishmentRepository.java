package com.adera.repositories;

import com.adera.database.ConnectionMySQL;
import com.adera.entities.EstablishmentEntity;
import com.adera.extensions.MySQLExtension;
import jdk.jshell.spi.ExecutionControl;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class EstablishmentRepository {

    private final Connection conn;

    public EstablishmentRepository(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<EstablishmentEntity> getAll() throws SQLException {
        String query = "SELECT * FROM estabelecimento";
        Statement statement = conn.createStatement();
        try {
            ResultSet result = statement.executeQuery(query);

            ArrayList<EstablishmentEntity> list = new ArrayList<>();

            while(result.next()) {
                EstablishmentEntity market = new EstablishmentEntity();
                market.setId(UUID.fromString(result.getString(1)));
                market.setFantasyName(result.getString(2));
                market.setCnpj(result.getString(3));
                list.add(market);
            }

            return list;
        } catch (SQLException e) {
            MySQLExtension.handleException(e);
            return new ArrayList<>();
        }
    }

    public EstablishmentEntity getOneById(String id) throws SQLException {
        assert conn != null;
        String query = "select * from estabelecimento where id = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        try {
            statement.setString(1, id);
            statement.execute();
            ResultSet result = statement.getResultSet();

            EstablishmentEntity ec = new EstablishmentEntity();
            while(result.next()) {
                ec.setId(UUID.fromString(result.getString(1)));
                ec.setFantasyName(result.getString(2));
                ec.setCnpj(result.getString(3));
            }
            return ec;
        } catch (SQLException e) {
            MySQLExtension.handleException(e);
            return null;
        }
    }

    public ResultSet insertOne(EstablishmentEntity market) throws SQLException {
        assert conn != null;
        String query = "INSERT INTO estabelecimento VALUES (?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(query);
        try {
            statement.setString(1, market.getId().toString());
            statement.setString(2, market.getFantasyName());
            statement.setString(3, market.getCnpj());
            statement.execute();
            return statement.getResultSet();
        } catch (SQLException e) {
            MySQLExtension.handleException(e);
            return null;
        }
    }
}
