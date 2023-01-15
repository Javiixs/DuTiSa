package com.github.badaccuracy.id.dutisa.database.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQL {

    private final String host;
    private final int port;
    private final String database;
    private final String username;
    private final String password;
    private final boolean useSSL;

    private Connection connection;

    public MySQL(String host, int port, String database, String username, String password, boolean useSSL) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.useSSL = useSSL;
    }

    public void connect() throws SQLException {
        String url = this.formatUrl(host, port, database, useSSL);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        Properties properties = new Properties();
        properties.setProperty("user", username);
        properties.setProperty("password", password);


        properties.setProperty("useServerPrepStmts", "false");
        properties.setProperty("cachePrepStmts", "true");
        properties.setProperty("prepStmtCacheSize", "250");
        properties.setProperty("prepStmtCacheSqlLimit", "2048");
        properties.setProperty("autoReconnect", "true");

        this.connection = DriverManager.getConnection(url, properties);
    }

    public void disconnect() throws SQLException {
        connection.close();
        connection = null;
    }

    public void executeQuery(String query) throws SQLException {
        new Query(query, this).execute();
    }

    public Results results(String query) throws SQLException {
        return new Query(query, this).getResults();
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    private String formatUrl(String host, int port, String database, boolean useSSL) {
        if (database == null)
            database = "";
        if (host == null)
            host = "";

        if (!database.isEmpty() && !database.startsWith("/")) {
            database = "/" + database;
        }

        if (useSSL) {
            return "jdbc:mysql://" + host + ":" + port + database + "?useSSL=true";
        } else {
            return "jdbc:mysql://" + host + ":" + port + database + "?useSSL=false";
        }
    }
}
