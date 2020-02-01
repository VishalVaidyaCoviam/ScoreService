package com.example.scoreboard.repository;

import com.example.scoreboard.document.TempScoreStatic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TempScoreStaticRepository extends MongoRepository<TempScoreStatic,String> {


    Optional<TempScoreStatic> findByUserIdAndQuizId(String UserId, String quizId);
}
