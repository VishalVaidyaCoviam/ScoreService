package com.example.scoreboard.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Time;

@Data
@Document

public class TempScoreStatic {
    @Id
    private String userId;
    private int score;
    private String quizId;
}
