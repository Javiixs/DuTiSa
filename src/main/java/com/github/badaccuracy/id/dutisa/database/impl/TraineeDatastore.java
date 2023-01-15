package com.github.badaccuracy.id.dutisa.database.impl;

import com.github.badaccuracy.id.dutisa.DuTiSa;
import com.github.badaccuracy.id.dutisa.database.connector.MySQL;
import com.github.badaccuracy.id.dutisa.database.connector.Results;
import com.github.badaccuracy.id.dutisa.database.objects.LoginData;
import com.github.badaccuracy.id.dutisa.database.objects.TraineeData;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TraineeDatastore {

    private final DuTiSa duTiSa;
    @Getter
    private final ConcurrentHashMap<String, TraineeData> traineeMap;
    private final MySQL mySQL;

    public TraineeDatastore(DuTiSa duTiSa, MySQL mySQL) {
        this.duTiSa = duTiSa;
        this.traineeMap = new ConcurrentHashMap<>();
        this.mySQL = mySQL;

        duTiSa.getExecutorManager().gocExecutor("TraineeUL")
                .execute(() -> {
                    try {
                        mySQL.executeQuery("CREATE TABLE IF NOT EXISTS trainees (" +
                                "trainee_number VARCHAR(255) NOT NULL," +
                                "trainee_name VARCHAR(255) NOT NULL," +
                                "password VARCHAR(255) NOT NULL," +
                                "PRIMARY KEY (trainee_number)" +
                                ");");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                });
    }

    public void addTrainee(TraineeData traineeData) {
        traineeMap.put(traineeData.getTraineeNumber(), traineeData);
    }

    public TraineeData getTrainee(String traineeNumber) {
        return traineeMap.get(traineeNumber);
    }

    public void reloadTrainees() {
        traineeMap.clear();
        this.loadTrainees();
    }

    private void insertNewTrainee(TraineeData traineeData) {
        this.insertNewTraineeAsync(traineeData);
    }

    private void loadTrainees() {
        duTiSa.getExecutorManager().gocExecutor("TraineeDL")
                .execute(() -> {
                    try (Results results = mySQL.results("SELECT * FROM trainees;")) {
                        while (true) {
                            ResultSet set = results.getResultSet();
                            if (!set.next())
                                break;

                            TraineeData traineeData = new TraineeData(
                                    set.getString("trainee_number"),
                                    set.getString("trainee_name")
                            );

                            LoginData loginData = new LoginData(
                                    set.getString("trainee_number"),
                                    set.getString("password")
                            );
                            traineeData.setLoginData(loginData);
                            addTrainee(traineeData);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    private synchronized void insertNewTraineeAsync(TraineeData traineeData) {
        duTiSa.getExecutorManager().gocExecutor("TraineeUL")
                .execute(() -> {
                    try {
                        mySQL.executeQuery("INSERT INTO trainees (trainee_number, trainee_name, password) VALUES ('" +
                                traineeData.getTraineeNumber() + "', '" +
                                traineeData.getTraineeName() + "', '" +
                                traineeData.getLoginData().getHashedPassword() + "');");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
