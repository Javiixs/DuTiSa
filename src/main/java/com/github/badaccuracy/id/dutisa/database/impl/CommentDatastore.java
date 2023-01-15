package com.github.badaccuracy.id.dutisa.database.impl;

import com.github.badaccuracy.id.dutisa.DuTiSa;
import com.github.badaccuracy.id.dutisa.database.connector.MySQL;
import com.github.badaccuracy.id.dutisa.database.connector.Results;
import com.github.badaccuracy.id.dutisa.database.objects.CommentData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class CommentDatastore {

    private final DuTiSa duTiSa;
    private final MySQL mySQL;
    private final ConcurrentHashMap<Integer, CommentData> commentMap;

    public CommentDatastore(DuTiSa duTiSa, MySQL mySQL) {
        this.duTiSa = duTiSa;
        this.mySQL = mySQL;
        this.commentMap = new ConcurrentHashMap<>();

        duTiSa.getExecutorManager().gocExecutor("CommentUL")
                .execute(() -> {
                    duTiSa.getExecutorManager().gocExecutor("CommentDL")
                            .execute(() -> {
                                try {
                                    mySQL.executeQuery("CREATE TABLE IF NOT EXISTS comments (" +
                                            "comment_id VARCHAR(255) NOT NULL AUTO_INCREMENT," +
                                            "trainee_number VARCHAR(255) NOT NULL," +
                                            "comment VARCHAR(255) NOT NULL," +
                                            "commenter VARCHAR(255) NOT NULL," +
                                            "date VARCHAR(255) NOT NULL," +
                                            "PRIMARY KEY (comment_id)," +
                                            "FOREIGN KEY (trainee_number) REFERENCES trainees (trainee_number)" +
                                            ");");
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            });
                });
        this.loadComments();
    }

    public void addComment(CommentData commentData) {
        commentMap.put(commentData.getCommentId(), commentData);
    }

    public CommentData getComment(Integer commentId) {
        return commentMap.get(commentId);
    }

    public Iterator<CommentData> getComments(String traineeNumber) {
        return commentMap.values().stream()
                .filter(commentData -> commentData.getTraineeNumber().equals(traineeNumber)).iterator();
    }

    public void reloadComments() {
        commentMap.clear();
        this.loadComments();
    }

    public void insertNewComment(CommentData commentData) {
        insertNewCommentAsync(commentData);
    }

    private void loadComments() {
        duTiSa.getExecutorManager().gocExecutor("CommentDL")
                .execute(() -> {
                    try (Results results = mySQL.results("SELECT * FROM comments;")) {
                        while (true) {
                            ResultSet set = results.getResultSet();
                            if (!set.next())
                                break;

                            CommentData commentData = new CommentData(
                                    set.getInt("comment_id"),
                                    set.getString("trainee_number"),
                                    set.getString("comment"),
                                    set.getString("commenter"),
                                    set.getString("date")
                            );
                            addComment(commentData);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    private void insertNewCommentAsync(CommentData commentData) {
        duTiSa.getExecutorManager().gocExecutor("CommentUL")
                .execute(() -> {
                    try {
                        mySQL.executeQuery("INSERT INTO comments (trainee_number, comment, commenter, date) VALUES ('" +
                                commentData.getTraineeNumber() + "', '" +
                                commentData.getComment() + "', '" +
                                commentData.getCommenter() + "', '" +
                                commentData.getDate() + "')");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
    }
}
