package com.github.badaccuracy.id.dutisa.database.objects;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.File;

@Data
@RequiredArgsConstructor
public class TraineeData {

    private final String traineeNumber;
    private final String traineeName;
    private final String major;
    private final String binusian;
    private final File photo;

    private LoginData loginData = null;

}
