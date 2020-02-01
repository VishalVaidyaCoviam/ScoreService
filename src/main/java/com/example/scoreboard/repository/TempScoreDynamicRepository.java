package com.example.scoreboard.repository;

import com.example.scoreboard.document.TempScoreDynamic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TempScoreDynamicRepository extends MongoRepository<TempScoreDynamic,String> {
    Optional<TempScoreDynamic> findByUserIdAndQuizId(String UserId,String quizId);
    void deleteByUserIdAndQuizId(String UserId,String quizId);
    TempScoreDynamic findByQuizId(String QuizId);
//    TempScoreDynamic findByUserIdAndQuizId(String UserId,String quizId);
}
