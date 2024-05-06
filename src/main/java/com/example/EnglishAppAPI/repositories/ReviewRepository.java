package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.Review;
import com.example.EnglishAppAPI.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserWhoWasReviewed(UserEntity userWhoWasReviewed);
}
