package com.github.badaccuracy.id.dutisa;

import com.github.badaccuracy.id.dutisa.async.ExecutorManager;
import com.github.badaccuracy.id.dutisa.database.objects.LoginData;
import com.github.badaccuracy.id.dutisa.menu.LoginMenu;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;

public class DuTiSa extends Application {

    @Getter
    private final ExecutorManager executorManager = new ExecutorManager();

    public static void main(String[] args) {
        DuTiSa.launch(args);
    }

    @Override
    public void start(Stage stage) {
//        LoginData loginData = new LoginData();
//        loginData.traineeNumber;
        new LoginMenu();
    }

}
