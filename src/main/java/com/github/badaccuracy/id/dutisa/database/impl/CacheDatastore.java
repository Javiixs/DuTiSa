package com.github.badaccuracy.id.dutisa.database.impl;

import com.github.badaccuracy.id.dutisa.database.Datastore;
import com.github.badaccuracy.id.dutisa.database.objects.LoginData;
import com.github.badaccuracy.id.dutisa.database.objects.Trainee;

import java.util.HashMap;
import java.util.Map;

public class CacheDatastore implements Datastore {

    private final Datastore sqlDatastore;
    private final Map<String, Trainee> traineeCache;

    public CacheDatastore(Datastore sqlDatastore) {
        this.sqlDatastore = sqlDatastore;
        this.traineeCache = new HashMap<>();
    }

    @Override
    public boolean checkLogin(LoginData loginData) {
        if (traineeCache.containsKey(loginData.getTraineeNumber())) {
            return traineeCache.get(loginData.getTraineeNumber()).getLoginData().equals(loginData);
        }

        return false;
    }

    @Override
    public Trainee getTrainee(String traineeNumber) {
        return traineeCache.getOrDefault(traineeNumber, sqlDatastore.getTrainee(traineeNumber));
    }
}
