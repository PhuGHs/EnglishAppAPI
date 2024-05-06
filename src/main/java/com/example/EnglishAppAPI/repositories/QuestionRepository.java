package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.EnglishTest;
import com.example.EnglishAppAPI.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findQuestionsByEnglishTest(EnglishTest englishTest);
}
