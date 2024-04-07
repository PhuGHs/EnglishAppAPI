package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.EnglishTopic;
import com.example.EnglishAppAPI.entities.EnglishTopicQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnglishTopicQuestionRepository extends JpaRepository<EnglishTopicQuestion, Long> {
}
