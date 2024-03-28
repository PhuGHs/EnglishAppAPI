package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.EnglishTopicQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnglishTopicQuestionRepository extends JpaRepository<EnglishTopicQuestion, Long> {
}
