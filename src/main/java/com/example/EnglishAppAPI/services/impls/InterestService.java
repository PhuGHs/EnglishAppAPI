package com.example.EnglishAppAPI.services.impls;

import com.example.EnglishAppAPI.entities.Interest;
import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.mapstruct.dtos.InterestPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.InterestPutDto;
import com.example.EnglishAppAPI.mapstruct.mappers.InterestMapper;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.repositories.InterestRepository;
import com.example.EnglishAppAPI.repositories.UserRepository;
import com.example.EnglishAppAPI.responses.ApiResponseStatus;
import com.example.EnglishAppAPI.services.interfaces.IInterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class InterestService implements IInterestService {
    @Autowired
    private InterestRepository interestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InterestMapper interestMapper;
    @Override
    public ResponseEntity<?> createNewInterest(InterestPostDto interestPostDto) {
        Interest interest = interestMapper.toEntity(interestPostDto);
        interestRepository.save(interest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ApiResponseStatus.SUCCESS, "created new interest", interestMapper.toDto(interest)));
    }

    @Override
    public ResponseEntity<ApiResponse> getPeopleWithSimilarInterests(Long userId) {
        return null;
    }

    @Override
    public ResponseEntity<?> selectInterests(InterestPutDto interestPutDto) {
        UserEntity user = userRepository.findById(interestPutDto.getUserId())
                .orElseThrow(() -> new NotFoundException("user is not found"));
        Set<Long> interests = interestPutDto.getInterests();
        Set<Interest> selectedInterests = new HashSet<>();
        for (Long id: interests) {
            Interest interest = interestRepository.findById(id).orElse(null);
            if (interest == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(ApiResponseStatus.FAIL, "there is one interest not found", ""));
            }
            selectedInterests.add(interest);
        }
        user.setInterests(selectedInterests);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("selected interests");
    }

    @Override
    public ResponseEntity<ApiResponse> getUserInterests(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user is not found"));
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get user interests", user.getInterests()));
    }
}
