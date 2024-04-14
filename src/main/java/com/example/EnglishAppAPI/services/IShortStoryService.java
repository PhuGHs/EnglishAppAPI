package com.example.EnglishAppAPI.services;

import com.example.EnglishAppAPI.mapstruct.dtos.ShortStoryDto;
import com.example.EnglishAppAPI.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface IShortStoryService {
    ResponseEntity<ApiResponse> createNewShortStories(ShortStoryDto shortStoryDto);
    ResponseEntity<?> deleteShortStory(Long id);
    ResponseEntity<ApiResponse> updateShortStory(Long id, ShortStoryDto shortStoryDto);
    ResponseEntity<ApiResponse> getAllShortStories();
}
