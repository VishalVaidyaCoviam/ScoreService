package com.example.scoreboard.service;

import com.example.scoreboard.document.ScoreStatic;
import com.example.scoreboard.dto.StaticLeaderboardDTO;
import com.example.scoreboard.dto.SubmitDTO;

public interface ScoreStaticService {
    ScoreStatic findStatic(String userId , String quizId);
    public StaticLeaderboardDTO getStaticLeaderboard(String userId, String quizId);
    boolean submitScore(SubmitDTO submitDTO, boolean skip);
}
