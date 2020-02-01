package com.example.scoreboard.service.impl;

import com.example.scoreboard.document.SendQuestionDTO;
import com.example.scoreboard.document.TempScoreDynamic;
import com.example.scoreboard.dto.ValidationDTO;
import com.example.scoreboard.repository.TempScoreDynamicRepository;
import com.example.scoreboard.service.TempScoreDynamicService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TempScoreDynamicServiceImpl implements TempScoreDynamicService {

    @Autowired
    TempScoreDynamicRepository tempScoreDynamicRepository;

    @Override
    @CacheEvict(value = "question",key = "#sendQuestionDTO")
    public void updateScore(ValidationDTO validationDTO, int marks, Date time, SendQuestionDTO sendQuestionDTO) {
//        public void updateScore(ValidationDTO validationDTO, int marks, SendQuestionDTO sendQuestionDTO) {
        Optional<TempScoreDynamic> tempScoreDynamic;

        tempScoreDynamic = tempScoreDynamicRepository.findByUserIdAndQuizId(validationDTO.getUserId() , validationDTO.getQuizId());
        if (tempScoreDynamic.isPresent()) {
            TempScoreDynamic tempScoreDynamic1 = new TempScoreDynamic();
            BeanUtils.copyProperties(tempScoreDynamic.get(), tempScoreDynamic1);
            tempScoreDynamic1.setScore(tempScoreDynamic1.getScore() + marks);
            tempScoreDynamic1.setQuestionId(validationDTO.getQuestionId());
            tempScoreDynamic1.setDate(time);
            tempScoreDynamicRepository.save(tempScoreDynamic1);
        } else {
            TempScoreDynamic tempScoreDynamic1 = new TempScoreDynamic();
            tempScoreDynamic1.setUserId(validationDTO.getUserId());
            tempScoreDynamic1.setQuizId(validationDTO.getQuizId());
            tempScoreDynamic1.setScore(marks);
            tempScoreDynamic1.setDate(time);
            tempScoreDynamic1.setQuestionId(validationDTO.getQuestionId());
            tempScoreDynamicRepository.save(tempScoreDynamic1);
        }
        sendQuestion(sendQuestionDTO);
    }

    @Override
    @Cacheable(value = "question",key = "#sendQuestionDTO")
    public String sendQuestion(SendQuestionDTO sendQuestionDTO) {
        Optional<TempScoreDynamic> tempScore =  tempScoreDynamicRepository.findByUserIdAndQuizId(sendQuestionDTO.getUserId(),sendQuestionDTO.getQuizId());
        System.out.println("temp "+tempScore.get());
//        if(tempScore.isPresent())
            return tempScore.get().getQuestionId();
//        return "Not Found!";
    }

    @Override
    public void fastestFingerFirst(String quizId) {
        List<TempScoreDynamic> tempScorelist = (List<TempScoreDynamic>) tempScoreDynamicRepository.findByQuizId(quizId);
        tempScorelist.sort(Comparator.comparing(TempScoreDynamic::getDate));
        int marks = tempScorelist.get(0).getScore();
        marks++;
        tempScorelist.get(0).setScore(marks);
        int Counter =0;
        while(tempScorelist.get(Counter).getDate().compareTo(tempScorelist.get((Counter+1)).getDate()) == 0)
        {
            marks = tempScorelist.get(Counter+1).getScore();
            marks++;
            tempScorelist.get(0).setScore(Counter+1);
            Counter++;
        }
    }
}
