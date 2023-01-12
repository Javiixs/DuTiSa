package com.github.badaccuracy.id.dutisa.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private StackPane root_pane;
    @FXML
    private Label version_label;

    @FXML
    private AnchorPane login_pane;
    @FXML
    private TextField username_field;
    @FXML
    private PasswordField password_field;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onLoginAction() {
        String username = username_field.getText();
        String password = password_field.getText();

        // todo: check from database
    }

    public void onExitButton() {
        System.exit(0);
    }
}
