package com.example.scoreboard.repository;

import com.example.scoreboard.document.ScoreDynamic;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreDynamicRepository extends MongoRepository<ScoreDynamic , String> {
    List<ScoreDynamic> findAllByQuizId(String quizId , Sort score);


}
