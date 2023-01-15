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


    opens com.github.badaccuracy.id.dutisa to javafx.fxml;
    exports com.github.badaccuracy.id.dutisa;
}