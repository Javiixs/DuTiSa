package com.github.badaccuracy.id.dutisa.database.objects;

import lombok.Data;

@Data
public class CommentData {

    private final int commentId;
    private final String traineeNumber;
    private final String comment;
    private final String commenter;
    private final String date;

}
