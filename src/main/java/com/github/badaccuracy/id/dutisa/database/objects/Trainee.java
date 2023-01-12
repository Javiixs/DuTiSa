package com.github.badaccuracy.id.dutisa.database.objects;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Trainee {

    private final String traineeNumber;
    private final String traineeName;

    private LoginData loginData = null;
    private List<CommentData> comments = new ArrayList<>();

}
