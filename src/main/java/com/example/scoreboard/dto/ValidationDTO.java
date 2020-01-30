package com.example.scoreboard.dto;

import lombok.Data;

import java.util.List;

@Data
public class ValidationDTO {

    private String  userId;
    private String questionId;
    private String quizId;
    private String answerType;
    private String answer;
}
