package com.example.scoreboard.service.impl;

import com.example.scoreboard.document.ScoreDynamic;
import com.example.scoreboard.document.ScoreOverall;
import com.example.scoreboard.document.TempScoreDynamic;
import com.example.scoreboard.dto.DynamicLeaderboardDTO;
import com.example.scoreboard.dto.SubmitDTO;
import com.example.scoreboard.repository.ScoreOverallRepository;
import com.example.scoreboard.repository.TempScoreDynamicRepository;
import com.example.scoreboard.service.ScoreDynamicService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.scoreboard.repository.ScoreDynamicRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScoreDynamicServiceImpl implements ScoreDynamicService {

    @Autowired
    ScoreDynamicRepository scoreDynamicRepository;

    @Autowired
    TempScoreDynamicRepository tempScoreDynamicRepository;

    @Autowired
    ScoreOverallRepository scoreOverallRepository;

    @Override
    public void submitScore(SubmitDTO submitDynamicDTO) {
        ScoreDynamic scoreDynamic = new ScoreDynamic();
        Optional<ScoreOverall> scoreOverall;
        ScoreOverall scoreOverall1 = new ScoreOverall();
        scoreOverall=scoreOverallRepository.findById(submitDynamicDTO.getUserId());
        if(scoreOverall.isPresent()){
            BeanUtils.copyProperties(scoreOverall.get(), scoreOverall1 );
        }
        else {
            scoreOverall1.setUserId(submitDynamicDTO.getUserId());
            scoreOverall1.setScore(0);
        }
        Optional<TempScoreDynamic> tempScore;
        tempScore = tempScoreDynamicRepository.findByUserIdAndQuizId(submitDynamicDTO.getUserId() , submitDynamicDTO.getQuizId());
        if(tempScore.isPresent()){
            TempScoreDynamic tempScoreDynamic = new TempScoreDynamic();
            BeanUtils.copyProperties(tempScore.get(), tempScoreDynamic);
            scoreDynamic.setScore(tempScoreDynamic.getScore());
            scoreOverall1.setScore(scoreOverall1.getScore()+ tempScoreDynamic.getScore());
            }
        else {
            scoreDynamic.setUserId(submitDynamicDTO.getUserId());
            scoreDynamic.setQuizId(submitDynamicDTO.getQuizId());
            scoreDynamic.setScore(0);
            }
        scoreDynamicRepository.save(scoreDynamic);
        scoreOverallRepository.save(scoreOverall1);
        tempScoreDynamicRepository.deleteByUserIdAndQuizId(submitDynamicDTO.getUserId(),submitDynamicDTO.getQuizId());
    }

    @Override
    public DynamicLeaderboardDTO getDynamicLeaderboard(String userId, String quizId) {
        List<ScoreDynamic> board = new ArrayList<ScoreDynamic>();
        board = scoreDynamicRepository.findAllByQuizId(quizId , Sort.by(Sort.Direction.DESC, "score"));
        DynamicLeaderboardDTO dynamicLeaderboardDTO = new DynamicLeaderboardDTO();
        int rank = 0;
        for(int i=0 ; i<board.size() ; i++)
        {
            if(board.get(i).getUserId()==userId){
                rank=i+1;
            }
        }
        dynamicLeaderboardDTO.setRank(rank);
        dynamicLeaderboardDTO.setScoreDynamicList(board);
        return dynamicLeaderboardDTO;
    }


}
