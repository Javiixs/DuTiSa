package com.github.badaccuracy.id.dutisa.database.objects;

import lombok.Data;

@Data
public class DatabaseConfig {

    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final String database;
    private final boolean useSSL;

}
