package com.github.badaccuracy.id.dutisa.database.objects;

public class LoginData {

    private final String traineeNumber;
    private final String hashedPassword;

    public LoginData(String traineeNumber, String hashedPassword) {
        this.traineeNumber = traineeNumber;
        this.hashedPassword = hashedPassword;
    }

    public String getTraineeNumber() {
        return traineeNumber;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }




}
