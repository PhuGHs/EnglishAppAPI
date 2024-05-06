package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.mapstruct.dtos.ReviewPostDto;
import com.example.EnglishAppAPI.services.impls.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RestControllerAdvice
@Validated
@RequestMapping("${api.prefix}/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/{userId}/get")
    public ResponseEntity<?> getReviewsByUser(@PathVariable Long userId) {
        return reviewService.getReviews(userId);
    }

    @PostMapping("/add-review")
    public ResponseEntity<?> addReview(@Valid @RequestBody ReviewPostDto review) {
        return reviewService.reviewLearner(review);
    }
}
