package com.github.badaccuracy.id.dutisa.menu;

import com.github.badaccuracy.id.dutisa.DuTiSa;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.concurrent.atomic.AtomicReference;

public class LoginMenu {

    private StackPane root_pane;

    private BorderPane login_pane;
    private TextField username_field;
    private PasswordField password_field;

    public LoginMenu() {
        initialize();
    }

    private void initialize() {
        root_pane = new StackPane();
        login_pane = new BorderPane();
        VBox vBox = new VBox();
        username_field = new TextField();
        password_field = new PasswordField();

        root_pane.getChildren().add(login_pane);
        vBox.getChildren().add(username_field);
        vBox.getChildren().add(password_field);
        vBox.setLayoutY(400);
        vBox.setPrefWidth(250);

        login_pane.setCenter(vBox);

        root_pane.setPrefHeight(600);
        root_pane.setPrefWidth(800);

        username_field.setPromptText("Username");
        password_field.setPromptText("Password");

        AtomicReference<Double> x = new AtomicReference<>((double) 0);
        AtomicReference<Double> y = new AtomicReference<>((double) 0);
        root_pane.setOnMousePressed(event -> {
            x.set(event.getSceneX());
            y.set(event.getSceneY());
        });

        root_pane.setOnMouseDragged(event -> {
            x.set(event.getSceneX());
            y.set(event.getSceneY());
        });

        Scene scene = new Scene(root_pane);
    }

    private void show() {
        TextField email = new TextField();
        PasswordField password = new PasswordField();

        Button button = new Button("Login");
        button.setOnMouseClicked((event) -> {
            if (email.getText().isEmpty() || password.getText().isEmpty()) {
                return;
            }

            if (email.getText() == password.getText()) {

            }
        });
    }

    private void showErrorDialog() {
        // create dialog box without fxml
        DialogPane dialogPane = new DialogPane();
        dialogPane.setContentText("Invalid username or password");
        dialogPane.getButtonTypes().add(ButtonType.OK);
        dialogPane.setPrefSize(300, 100);
        dialogPane.setMinSize(300, 100);
        dialogPane.setMaxSize(300, 100);

        // create stage
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(dialogPane));
        stage.show();

        // blur background
        login_pane.setEffect(new BoxBlur(10, 10, 10));
        stage.setOnCloseRequest(event -> root_pane.setEffect(null));
    }

}
