package com.example.scoreboard.repository;


import com.example.scoreboard.document.ScoreStatic;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreStaticRepository extends MongoRepository<ScoreStatic,String> {
    List<ScoreStatic> findAllByQuizId(String quizId , Sort score);


    Optional<ScoreStatic> findByUserIdAndQuizId(String UserId, String quizId);
}
