package com.example.EnglishAppAPI.services.impls;

import com.example.EnglishAppAPI.entities.Review;
import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.mapstruct.dtos.NotificationPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.ReviewPostDto;
import com.example.EnglishAppAPI.mapstruct.mappers.ReviewMapper;
import com.example.EnglishAppAPI.repositories.ReviewRepository;
import com.example.EnglishAppAPI.repositories.UserRepository;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.responses.ApiResponseStatus;
import com.example.EnglishAppAPI.services.interfaces.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService implements IReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ReviewMapper reviewMapper, UserRepository userRepository, NotificationService notificationService) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    @Override
    public ResponseEntity<?> reviewLearner(ReviewPostDto reviewPostDto) {
        Review review = reviewMapper.toEntity(reviewPostDto);
        review = reviewRepository.save(review);
        notificationService.addNotification(new NotificationPostDto(reviewPostDto.getUserWhoReviewedId(), reviewPostDto.getUserWhoWasReviewedId(), "a user had reviewed you on your profile", false, LocalDateTime.now(), review.getReviewId(), review.getReviewId()));
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "review learner", reviewMapper.toDto(review)));
    }

    @Override
    public ResponseEntity<?> getReviews(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user is not found"));
        List<Review> reviews = reviewRepository.findByUserWhoWasReviewed(user);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get reviews", reviews.stream().map(reviewMapper::toDto)));
    }
}
