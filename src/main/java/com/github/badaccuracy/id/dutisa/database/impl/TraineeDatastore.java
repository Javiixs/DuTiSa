package com.github.badaccuracy.id.dutisa.database.impl;

import com.github.badaccuracy.id.dutisa.DuTiSa;
import com.github.badaccuracy.id.dutisa.database.connector.MySQL;
import com.github.badaccuracy.id.dutisa.database.connector.Results;
import com.github.badaccuracy.id.dutisa.database.objects.LoginData;
import com.github.badaccuracy.id.dutisa.database.objects.TraineeData;
import lombok.Getter;

import java.sql.ResultSet;
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

        this.loadTrainees();
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

    public void insertNewTrainee(TraineeData traineeData) {
        this.insertNewTraineeAsync(traineeData);
    }

    private void loadTrainees() {
        duTiSa.getExecutorManager().gocExecutor("TraineeDL")
                .execute(() -> {
                    try (Results results = mySQL.results("SELECT * FROM ListTrainee;")) {
                        while (true) {
                            ResultSet set = results.getResultSet();
                            if (!set.next())
                                break;

                            TraineeData traineeData = new TraineeData(
                                    set.getString("TraineeNumber"),
                                    set.getString("TraineeName"),
                                    set.getString("Jurusan"),
                                    set.getString("Angkatan"),
                                    set.getString("TraineePicture")
                            );

                            LoginData loginData = new LoginData(
                                    set.getString("TraineeNumber"),
                                    set.getString("TraineePassword")
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
                        String query = "INSERT INTO ListTrainee (TraineeNumber, TraineeName, TraineePassword, Jurusan, Angkatan, TraineePicture) VALUES ('" +
                                traineeData.getTraineeNumber() + "', '" +
                                traineeData.getTraineeName() + "', '" +
                                traineeData.getLoginData().getHashedPassword() + "', '" +
                                traineeData.getJurusan() + "', '" +
                                traineeData.getAngkatan() + "', '" +
                                traineeData.getPhoto() + "');";
                        System.out.println(query);
                        mySQL.executeQuery(query);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
