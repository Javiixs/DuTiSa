package com.github.badaccuracy.id.dutisa.database.objects;

public class LoginData {

    private final String traineeNumber;
    private final String username;
    private final String hashedPassword;

    public LoginData(String traineeNumber, String username, String hashedPassword) {
        this.traineeNumber = traineeNumber;
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

    public String getTraineeNumber() {
        return traineeNumber;
    }
    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }


}
