package com.github.badaccuracy.id.dutisa.database.objects;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TraineeData {

    private final String traineeNumber;
    private final String traineeName;

    private LoginData loginData = null;

}
