package com.example.scoreboard.service;

import com.example.scoreboard.dto.DynamicLeaderboardDTO;
import com.example.scoreboard.dto.SubmitDTO;

public interface ScoreDynamicService {
    void submitScore(SubmitDTO submitDynamicDTO);
    public DynamicLeaderboardDTO getDynamicLeaderboard(String userId, String quizId);
}
