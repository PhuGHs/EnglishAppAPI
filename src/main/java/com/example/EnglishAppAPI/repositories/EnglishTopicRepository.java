package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.EnglishLevel;
import com.example.EnglishAppAPI.entities.EnglishTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnglishTopicRepository extends JpaRepository<EnglishTopic, Long> {
    List<EnglishTopic> findByEnglishLevel(EnglishLevel level);
}
