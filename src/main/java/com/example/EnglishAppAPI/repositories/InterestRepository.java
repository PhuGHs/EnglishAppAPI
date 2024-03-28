package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestRepository extends JpaRepository<Interest, Long> {
}
