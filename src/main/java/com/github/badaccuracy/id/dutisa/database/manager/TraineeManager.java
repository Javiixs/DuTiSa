package com.github.badaccuracy.id.dutisa.database.manager;

import com.github.badaccuracy.id.dutisa.DuTiSa;
import com.github.badaccuracy.id.dutisa.database.connector.MySQL;
import com.github.badaccuracy.id.dutisa.database.impl.CommentDatastore;
import com.github.badaccuracy.id.dutisa.database.impl.TraineeDatastore;

public class TraineeManager {

    private final DuTiSa main;
    private final MySQL mySQL;
    private final CommentDatastore commentDatastore;
    private final TraineeDatastore traineeDatastore;

    public TraineeManager(DuTiSa main) {
        this.main = main;
        this.mySQL = new MySQL(
                "localhost",
                3308,
                "DuTiSa",
                "admin",
                "admin",
                false
        );

        this.commentDatastore = new CommentDatastore(main, mySQL);
        this.traineeDatastore = new TraineeDatastore(main, mySQL);
    }

}
