package com.adera.repositories;

import com.adera.database.ConnectionMySQL;
import com.adera.entities.MarketEntity;
import com.adera.extensions.MySQLExtension;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class MarketRepository {

    private static final Connection conn = ConnectionMySQL.getConnection();

    public static ArrayList<MarketEntity> getAll() throws SQLException {
        assert conn != null;
        String query = "SELECT * FROM market";
        Statement statement = conn.createStatement();
        try {
            ResultSet result = statement.executeQuery(query);

            ArrayList<MarketEntity> list = new ArrayList<>();

            while(result.next()) {
                MarketEntity market = new MarketEntity();
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

    public static ResultSet insertOne(MarketEntity market) throws SQLException {
        assert conn != null;
        String query = "INSERT INTO market VALUES (?, ?, ?)";
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
