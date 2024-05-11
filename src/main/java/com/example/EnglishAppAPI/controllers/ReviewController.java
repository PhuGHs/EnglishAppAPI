package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.mapstruct.dtos.ReviewPostDto;
import com.example.EnglishAppAPI.services.impls.ReviewService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RestControllerAdvice
@Validated
@RequestMapping("${api.prefix}/reviews")
@SecurityRequirement(name = "bearerAuth")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/{userId}/get")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> getReviewsByUser(@PathVariable Long userId) {
        return reviewService.getReviews(userId);
    }

    @PostMapping("/add-review")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> addReview(@Valid @RequestBody ReviewPostDto review) {
        return reviewService.reviewLearner(review);
    }
}
