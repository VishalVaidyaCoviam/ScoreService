package com.example.scoreboard.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
//import java.sql.Timestamp;

@Data
@Document
public class TempScoreDynamic {
    @Id
    private String userId;
    private String quizId;
    private int score;
    private String questionId;
    private Date date;
}
