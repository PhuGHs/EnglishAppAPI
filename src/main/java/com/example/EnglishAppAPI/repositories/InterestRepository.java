package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.Interest;
import com.example.EnglishAppAPI.responses.ApiResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {
}
