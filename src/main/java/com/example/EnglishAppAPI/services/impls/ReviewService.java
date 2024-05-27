package com.example.EnglishAppAPI.services.impls;

import com.example.EnglishAppAPI.entities.Review;
import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.mapstruct.dtos.NotificationDto;
import com.example.EnglishAppAPI.mapstruct.dtos.NotificationPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.ReviewPostDto;
import com.example.EnglishAppAPI.mapstruct.mappers.ReviewMapper;
import com.example.EnglishAppAPI.repositories.ReviewRepository;
import com.example.EnglishAppAPI.repositories.UserRepository;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.responses.ApiResponseStatus;
import com.example.EnglishAppAPI.services.interfaces.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ReviewService implements IReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ReviewMapper reviewMapper, UserRepository userRepository, NotificationService notificationService, SimpMessagingTemplate simpMessagingTemplate) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public ResponseEntity<?> reviewLearner(ReviewPostDto reviewPostDto) {
        if (reviewRepository.checkIfExists(reviewPostDto.getUserWhoWasReviewedId(), reviewPostDto.getUserWhoReviewedId())) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ApiResponse(ApiResponseStatus.FAIL, "You are not allowed to review a person many time", ""));
        }
        UserEntity userGetReviewed = userRepository.findById(reviewPostDto.getUserWhoWasReviewedId())
                .orElseThrow(() -> new NotFoundException("User get reviewed not found"));
        UserEntity userReviewed = userRepository.findById(reviewPostDto.getUserWhoWasReviewedId())
                .orElseThrow(() -> new NotFoundException("User reviewed not found"));

        Review review = Review.builder()
                .comment(reviewPostDto.getComment())
                .createdAt(new Date())
                .star(reviewPostDto.getStar())
                .userWhoReviewed(userReviewed)
                .userWhoWasReviewed(userGetReviewed)
                .build();

        Long userWhoWasReviewed = userGetReviewed.getUserId();
        userGetReviewed.setReviews_count(userGetReviewed.getReviews_count() + 1);
        Float averageRating = reviewRepository.getAverageRating(userWhoWasReviewed);
        userGetReviewed.setStar(Objects.requireNonNullElse(averageRating, 0.0f));

        review = reviewRepository.save(review);
        userRepository.save(userGetReviewed);

        NotificationDto notificationDto = notificationService.addNotification(new NotificationPostDto(reviewPostDto.getUserWhoReviewedId(), reviewPostDto.getUserWhoWasReviewedId(), "a user had reviewed you on your profile", false, review.getReviewId(), review.getReviewId()));
        simpMessagingTemplate.convertAndSend("topic/user/notification" + userWhoWasReviewed, notificationDto);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "review learner", reviewMapper.toDto(review)));
    }

    @Override
    public ResponseEntity<?> getReviews(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user is not found"));
        List<Review> reviews = reviewRepository.findByUserWhoWasReviewed(user.getUserId());
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get reviews", reviews.stream().map(reviewMapper::toDto)));
    }
}
