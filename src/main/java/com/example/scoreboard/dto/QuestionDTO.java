package com.example.scoreboard.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionDTO {
    private String questionId;
    private String question;
    private OptionsDTO options;
    private String answerType;
    private String questionType;
    private String binaryFilePath;
    private String difficulty;
    private String category;
    private String answer;


}
