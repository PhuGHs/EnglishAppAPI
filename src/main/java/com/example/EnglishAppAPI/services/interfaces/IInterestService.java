package com.example.EnglishAppAPI.services.interfaces;

import com.example.EnglishAppAPI.entities.Interest;
import com.example.EnglishAppAPI.mapstruct.dtos.InterestPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.InterestPutDto;
import com.example.EnglishAppAPI.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public interface IInterestService {
    ResponseEntity<?> createNewInterest(InterestPostDto interestPostDto);
    ResponseEntity<ApiResponse> getPeopleWithSimilarInterests(Long userId);
    ResponseEntity<?> selectInterests(InterestPutDto interestPutDto);
    ResponseEntity<ApiResponse> getUserInterests(Long userId);
    ResponseEntity<?> getInterests();
}
