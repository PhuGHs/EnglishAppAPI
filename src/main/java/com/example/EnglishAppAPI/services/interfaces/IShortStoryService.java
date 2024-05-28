package com.example.EnglishAppAPI.services.interfaces;

import com.example.EnglishAppAPI.mapstruct.dtos.ShortStoryPostDto;
import com.example.EnglishAppAPI.mapstruct.enums.ShortStoryOrderBy;
import com.example.EnglishAppAPI.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface IShortStoryService {
    ResponseEntity<ApiResponse> createNewShortStories(ShortStoryPostDto shortStoryPostDto);
    ResponseEntity<?> deleteShortStory(Long id);
    ResponseEntity<ApiResponse> updateShortStory(Long id, ShortStoryPostDto shortStoryPostDto);
    ResponseEntity<ApiResponse> getAllShortStories(int pageNumber, int pageSize, ShortStoryOrderBy sortBy);
    ResponseEntity<ApiResponse> likeStory(Long id);
    ResponseEntity<?> getShortStory(Long id);
    ResponseEntity<?> getRandom5ShortStories(Long shortStoryId);
}
