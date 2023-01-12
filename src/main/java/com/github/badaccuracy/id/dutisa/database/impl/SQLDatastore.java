package com.github.badaccuracy.id.dutisa.database.impl;

import com.github.badaccuracy.id.dutisa.database.Datastore;
import com.github.badaccuracy.id.dutisa.database.objects.LoginData;
import com.github.badaccuracy.id.dutisa.database.objects.Trainee;

public class SQLDatastore implements Datastore {


    @Override
    public boolean checkLogin(LoginData loginData) {
        return false;
    }

    @Override
    public Trainee getTrainee(String traineeNumber) {
        return null;
    }
}
