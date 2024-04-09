package com.example.EnglishAppAPI.services;

import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.models.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface IUserService {
    ResponseEntity<ApiResponse> followUsers(Long currentUserId, Long id);
    ResponseEntity<ApiResponse> unfollowUsers(Long currentUserId, Long id);
}
