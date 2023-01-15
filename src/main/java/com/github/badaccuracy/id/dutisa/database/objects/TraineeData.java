package com.github.badaccuracy.id.dutisa.database.objects;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TraineeData {

    private final String traineeNumber;
    private final String traineeName;
    private final String jurusan;
    private final String angkatan;
    private final String photo;

    private LoginData loginData = null;

}
