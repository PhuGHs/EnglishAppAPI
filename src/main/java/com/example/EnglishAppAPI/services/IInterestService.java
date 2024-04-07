package com.example.EnglishAppAPI.services;

import com.example.EnglishAppAPI.entities.Interest;
import com.example.EnglishAppAPI.models.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public interface IInterestService {
    ResponseEntity<?> createNewInterest(String interestName);
    ResponseEntity<ApiResponse> getPeopleWithSimilarInterests(Long userId);
    ResponseEntity<?> selectInterests(Long userId, Set<Interest> interests);
}
