package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Long, Question> {
}
