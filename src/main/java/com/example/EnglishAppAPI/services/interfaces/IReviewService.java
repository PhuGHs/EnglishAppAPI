package com.example.EnglishAppAPI.services.interfaces;

import com.example.EnglishAppAPI.mapstruct.dtos.ReviewPostDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface IReviewService {
    ResponseEntity<?> reviewLearner(ReviewPostDto reviewPostDto);
    ResponseEntity<?> getReviews(Long userId);
}
