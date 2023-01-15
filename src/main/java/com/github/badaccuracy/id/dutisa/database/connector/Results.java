package com.github.badaccuracy.id.dutisa.database.connector;

import lombok.Getter;

import java.sql.ResultSet;
import java.sql.Statement;

@Getter
public class Results implements AutoCloseable {

    private final Statement statement;
    private final ResultSet resultSet;

    public Results(Statement statement, ResultSet resultSet) {
        this.statement = statement;
        this.resultSet = resultSet;
    }

    @Override
    public void close() {
        try {
            resultSet.close();
        } catch (Exception ignored) {
        }

        try {
            statement.close();
        } catch (Exception ignored) {
        }
    }
}
