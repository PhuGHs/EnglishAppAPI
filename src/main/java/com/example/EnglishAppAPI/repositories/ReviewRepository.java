package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.Review;
import com.example.EnglishAppAPI.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.userWhoWasReviewed.userId = :userId")
    List<Review> findByUserWhoWasReviewed(@Param("userId") Long userId);

    @Query("SELECT ROUND(AVG (r.star)) as average_rating FROM Review r WHERE r.userWhoWasReviewed.userId = :userId")
    Float getAverageRating(@Param("userId") Long userId);

    @Query("SELECT COUNT (r) > 0 FROM Review r WHERE r.userWhoWasReviewed.userId = :userWhoWasReviewedId and r.userWhoReviewed.userId = :userWhoReviewedId")
    boolean checkIfExists(@Param("userWhoWasReviewedId") Long userWhoWasReviewedId, @Param("userWhoReviewedId") Long userWhoReviewedId);
}
