module com.github.badaccuracy.id.dutisa {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    requires lombok;
    requires org.jetbrains.annotations;
    requires java.sql;
    requires mysql.connector.java;

    requires com.google.api.client;
    requires com.google.api.client.auth;
    requires com.google.api.client.json.gson;
    requires com.google.api.services.sheets;
    requires com.google.common;
    requires com.google.api.client.extensions.java6.auth;
    requires com.google.api.client.extensions.jetty.auth;
    requires google.api.client;

    requires com.google.gson;

    opens com.github.badaccuracy.id.dutisa;
    exports com.github.badaccuracy.id.dutisa;
}