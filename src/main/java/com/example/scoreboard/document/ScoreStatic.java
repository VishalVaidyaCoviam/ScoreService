package com.example.scoreboard.document;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class ScoreStatic {

    private String userId;
    private String quizId;
    private int score;
}
