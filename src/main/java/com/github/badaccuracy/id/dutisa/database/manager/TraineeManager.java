package com.github.badaccuracy.id.dutisa.database.manager;

import com.github.badaccuracy.id.dutisa.DuTiSa;
import com.github.badaccuracy.id.dutisa.database.connector.MySQL;
import com.github.badaccuracy.id.dutisa.database.impl.CommentDatastore;
import com.github.badaccuracy.id.dutisa.database.impl.TraineeDatastore;
import com.github.badaccuracy.id.dutisa.database.objects.DatabaseConfig;
import com.github.badaccuracy.id.dutisa.database.objects.LoginData;
import com.github.badaccuracy.id.dutisa.database.objects.TraineeData;
import com.github.badaccuracy.id.dutisa.utils.Utils;

import java.io.File;
import java.sql.SQLException;

public class TraineeManager {

    private final DuTiSa main;
    private final MySQL mySQL;
    private CommentDatastore commentDatastore;
    private TraineeDatastore traineeDatastore;


    public TraineeManager(DuTiSa main, DatabaseConfig databaseConfig) {
        this.main = main;
        this.mySQL = new MySQL(
                databaseConfig.getHost(),
                databaseConfig.getPort(),
                databaseConfig.getDatabase(),
                databaseConfig.getUsername(),
                databaseConfig.getPassword(),
                databaseConfig.isUseSSL()
        );

        main.getExecutorManager().gocExecutor("MySQLLoader")
                .execute(() -> {
                    try {
                        this.mySQL.connect();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    this.commentDatastore = new CommentDatastore(main, mySQL);
                    this.traineeDatastore = new TraineeDatastore(main, mySQL);
                });
    }

    public boolean canLogin(String traineeNumber, String password) {
        String hashedPassword = Utils.hashPassword(password);        return traineeDatastore.getTrainee(traineeNumber).getLoginData().getHashedPassword().equals(hashedPassword);
    }

    public boolean canRegister(String traineeNumber, String traineeName, String password, String jurusan, String angkatan, File photo) {
        TraineeData trainee = traineeDatastore.getTrainee(traineeNumber);
        if (trainee != null) {
            return false;
        }

        String hashedPassword = Utils.hashPassword(password);
        String imageBase64 = Utils.imageToBase64(photo);
        TraineeData traineeData = new TraineeData(traineeNumber, traineeName, jurusan, angkatan, imageBase64);

        LoginData loginData = new LoginData(traineeNumber, hashedPassword);
        traineeData.setLoginData(loginData);

        traineeDatastore.addTrainee(traineeData);
        traineeDatastore.insertNewTrainee(traineeData);
        return true;
    }

}
