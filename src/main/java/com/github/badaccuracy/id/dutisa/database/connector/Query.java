package com.github.badaccuracy.id.dutisa.database.connector;

import com.github.badaccuracy.id.dutisa.utils.Closer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    public Results getResults() throws SQLException {
        Connection connection = mySQL.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        return new Results(statement, resultSet);
    }
}
