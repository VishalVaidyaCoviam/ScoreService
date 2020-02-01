package com.example.scoreboard.service.impl;

import com.example.scoreboard.document.TempScoreStatic;
import com.example.scoreboard.dto.ValidationDTO;
import com.example.scoreboard.repository.TempScoreStaticRepository;
import com.example.scoreboard.service.TempScoreStaticService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TempScoreStaticServiceImpl implements TempScoreStaticService {

    @Autowired
    TempScoreStaticRepository tempScoreStaticRepository;

    @Override
    public void updateScore(ValidationDTO validationdto, int marks) {
        Optional<TempScoreStatic> tempScore1;

        tempScore1 = tempScoreStaticRepository.findByUserIdAndQuizId(validationdto.getUserId() , validationdto.getQuizId());
        if (tempScore1.isPresent()) {
            TempScoreStatic tempScoreStatic = new TempScoreStatic();
            System.out.println(tempScore1);
            BeanUtils.copyProperties(tempScore1.get(), tempScoreStatic);
            tempScoreStatic.setScore(tempScoreStatic.getScore() + marks);
            tempScoreStaticRepository.save(tempScoreStatic);
        } else {
            TempScoreStatic tempScoreStatic = new TempScoreStatic();
            tempScoreStatic.setUserId(validationdto.getUserId());
            tempScoreStatic.setQuizId(validationdto.getQuizId());
            tempScoreStatic.setScore(marks);
            tempScoreStaticRepository.save(tempScoreStatic);
        }
    }
}
