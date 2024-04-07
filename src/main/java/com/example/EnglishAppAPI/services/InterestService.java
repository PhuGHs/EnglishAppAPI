package com.example.EnglishAppAPI.services;

import com.example.EnglishAppAPI.entities.Interest;
import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.models.ApiResponse;
import com.example.EnglishAppAPI.repositories.InterestRepository;
import com.example.EnglishAppAPI.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class InterestService implements IInterestService {
    @Autowired
    private InterestRepository interestRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public ResponseEntity<?> createNewInterest(String interestName) {
        Interest interest = Interest.builder()
                .interestName(interestName)
                .build();
        interestRepository.save(interest);
        return ResponseEntity.status(HttpStatus.CREATED).body("created successfully");
    }

    @Override
    public ResponseEntity<ApiResponse> getPeopleWithSimilarInterests(Long userId) {
        return null;
    }

    @Override
    public ResponseEntity<?> selectInterests(Long userId, Set<Interest> interests) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user is not found"));
        user.setInterests(interests);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("selected interests");
    }
}
