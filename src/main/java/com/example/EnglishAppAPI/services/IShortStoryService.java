package com.example.EnglishAppAPI.services;

import com.example.EnglishAppAPI.models.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface IShortStoryService {
    ResponseEntity<ApiResponse> createNewShortStories();
    ResponseEntity<ApiResponse> deleteShortStory(Long id);
    ResponseEntity<ApiResponse> updateShortStory(Long id);
    ResponseEntity<ApiResponse> getAllShortStories();
}
