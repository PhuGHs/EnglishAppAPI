package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.EnglishTopic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnglishTopicRepository extends JpaRepository<EnglishTopic, Long> {
}
