package com.github.badaccuracy.id.dutisa.database;

import com.github.badaccuracy.id.dutisa.database.objects.LoginData;
import com.github.badaccuracy.id.dutisa.database.objects.Trainee;

public interface Datastore {

    boolean checkLogin(LoginData loginData);

    Trainee getTrainee(String traineeNumber);

}
