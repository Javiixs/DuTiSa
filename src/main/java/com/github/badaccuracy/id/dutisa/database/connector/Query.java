package com.github.badaccuracy.id.dutisa.database.connector;

import com.github.badaccuracy.id.dutisa.utils.Closer;

import java.sql.*;

public class Query {

    private final String query;
    private final MySQL mySQL;

    public Query(String query, MySQL mySQL) {
        this.query = query;
        this.mySQL = mySQL;
    }

    public void execute() throws SQLException {
        try (Closer closer = new Closer()) {
            Connection connection = mySQL.getConnection();
            Statement statement = closer.add(connection.createStatement());

            statement.execute(query);
        }
    }

    public PreparedStatement prepare() throws SQLException {
        Connection connection = mySQL.getConnection();
        return connection.prepareStatement(query);
    }

    public Results getResults() throws SQLException {
        Connection connection = mySQL.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        return new Results(statement, resultSet);
    }
}
