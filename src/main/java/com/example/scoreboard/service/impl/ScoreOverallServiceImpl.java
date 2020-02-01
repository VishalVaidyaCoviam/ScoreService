package com.example.scoreboard.service.impl;

import com.example.scoreboard.document.ScoreOverall;
import com.example.scoreboard.response.APIResponse;
import com.example.scoreboard.service.ScoreOverallService;
import com.example.scoreboard.repository.ScoreOverallRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScoreOverallServiceImpl implements ScoreOverallService {

    @Autowired
    ScoreOverallRepository scoreOverallRepository;

    @Override
    public ScoreOverall findOverallById(String id) {
        Optional<ScoreOverall> scoreOverall = scoreOverallRepository.findById(id);
        if (scoreOverall.isPresent()) {
            ScoreOverall scoreOverall1 = new ScoreOverall();
            BeanUtils.copyProperties(scoreOverall.get(), scoreOverall1);
            return scoreOverall1;
        }
        return null;
    }

    @Override
    public List<ScoreOverall> findAll() {
        APIResponse response = new APIResponse();
        List<ScoreOverall> leaderboard;
        leaderboard = scoreOverallRepository.findAll(Sort.by(Sort.Direction.DESC, "score"));
        return leaderboard;
    }
}
