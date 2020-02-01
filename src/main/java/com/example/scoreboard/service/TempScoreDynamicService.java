package com.example.scoreboard.service;

import com.example.scoreboard.document.SendQuestionDTO;
import com.example.scoreboard.dto.ValidationDTO;

import java.util.Date;
import java.sql.Timestamp;

public interface TempScoreDynamicService {
    void updateScore(ValidationDTO ValidationDTO, int marks , Date time, SendQuestionDTO sendQuestionDTO);
//    void updateScore(ValidationDTO ValidationDTO, int marks , SendQuestionDTO sendQuestionDTO);
    public void fastestFingerFirst(String quizId);
    public String sendQuestion(SendQuestionDTO sendQuestionDTO);
}
