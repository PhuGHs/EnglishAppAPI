package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.EnglishLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnglishLevelRepository extends JpaRepository<EnglishLevel, Long> {
}
