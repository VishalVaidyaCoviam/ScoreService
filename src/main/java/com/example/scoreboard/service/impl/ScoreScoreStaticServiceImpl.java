package com.example.scoreboard.service.impl;

import com.example.scoreboard.document.ScoreOverall;
import com.example.scoreboard.document.ScoreStatic;
import com.example.scoreboard.document.TempScoreStatic;
import com.example.scoreboard.dto.StaticLeaderboardDTO;
import com.example.scoreboard.dto.SubmitDTO;
import com.example.scoreboard.repository.ScoreOverallRepository;
import com.example.scoreboard.repository.TempScoreStaticRepository;
import com.example.scoreboard.service.ScoreStaticService;
import com.example.scoreboard.repository.ScoreStaticRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScoreScoreStaticServiceImpl implements ScoreStaticService {

    @Autowired
    ScoreStaticRepository scoreStaticRepository;

    @Autowired
    TempScoreStaticRepository tempScoreStaticRepository;

    @Autowired
    ScoreOverallRepository scoreOverallRepository;


    @Override
    public ScoreStatic findStatic(String userId , String quizId) {
        Optional<ScoreStatic> static1= scoreStaticRepository.findByUserIdAndQuizId(userId,quizId);
        ScoreStatic static2 = new ScoreStatic();
        BeanUtils.copyProperties(static1.get(),static2);
            return static2;
    }

    @Override
    public boolean submitScore(SubmitDTO submitDTO, boolean skip) {
        Optional<TempScoreStatic> tempScore1;
        TempScoreStatic tempScore = new TempScoreStatic();
        tempScore1 = tempScoreStaticRepository.findByUserIdAndQuizId(submitDTO.getUserId() , submitDTO.getQuizId());
        if(tempScore1.isPresent()) {
            BeanUtils.copyProperties(tempScore1,tempScore);
        }
        else {
            tempScore.setScore(0);
            tempScore.setUserId(submitDTO.getUserId());
            tempScore.setQuizId(submitDTO.getQuizId());
        }
        Optional<ScoreOverall> scoreOverall;
        scoreOverall=scoreOverallRepository.findById(submitDTO.getUserId());
        ScoreOverall scoreOverall1 = new ScoreOverall();
        if(scoreOverall.isPresent())
        {
            BeanUtils.copyProperties(scoreOverall.get(), scoreOverall1 );
        }
        else {
            scoreOverall1.setScore(0);
            scoreOverall1.setUserId(tempScore.getUserId());
        }

            Optional<ScoreStatic> scoreStatic1;
            scoreStatic1 = scoreStaticRepository.findByUserIdAndQuizId(submitDTO.getUserId(), submitDTO.getQuizId());
            if(scoreStatic1.isPresent())
            {
                if(scoreStatic1.get().getScore()<(tempScore.getScore()+5)){
                    ScoreStatic scoreStatic2 = new ScoreStatic();
                    BeanUtils.copyProperties(scoreStatic1.get() , scoreStatic2);
                    if(!skip) {
                        scoreStatic2.setScore(tempScore.getScore() + 5);
                        scoreOverall1.setScore(scoreOverall1.getScore()+ tempScore.getScore()+5);
                    }
                    else {
                        scoreStatic2.setScore(tempScore.getScore());
                        scoreOverall1.setScore(scoreOverall1.getScore()+ tempScore.getScore());
                    }
                    scoreStaticRepository.save(scoreStatic2);
                    scoreOverallRepository.save(scoreOverall1);
                    return true;
                }
                return true;
            }
            else {
                ScoreStatic scoreStatic = new ScoreStatic();
                scoreStatic.setUserId(tempScore.getUserId());
                scoreStatic.setQuizId(tempScore.getQuizId());
                if(!skip) {
                    scoreStatic.setScore(tempScore.getScore() + 5);
                    scoreOverall1.setScore(scoreOverall1.getScore() + tempScore.getScore() + 5);
                }
                else{
                    scoreStatic.setScore(tempScore.getScore());
                    scoreOverall1.setScore(scoreOverall1.getScore() + tempScore.getScore());
                }
                scoreStaticRepository.save(scoreStatic);
                scoreOverallRepository.save(scoreOverall1);
                return true;
            }
    }

    @Override
    public StaticLeaderboardDTO getStaticLeaderboard(String userId, String quizId) {
        List<ScoreStatic> board = new ArrayList<ScoreStatic>();
        board = scoreStaticRepository.findAllByQuizId(quizId , Sort.by(Sort.Direction.DESC, "score"));
        StaticLeaderboardDTO staticLeaderboardDTO = new StaticLeaderboardDTO();
        int rank = 0;
        for(int i=0 ; i<board.size() ; i++)
        {
            if(board.get(i).getUserId()==userId){
                rank=i+1;
            }
        }
        staticLeaderboardDTO.setRank(rank);
        staticLeaderboardDTO.setScoreStaticList(board);
        return staticLeaderboardDTO;
    }


}
