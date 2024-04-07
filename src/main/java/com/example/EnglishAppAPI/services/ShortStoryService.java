package com.example.EnglishAppAPI.services;

import com.example.EnglishAppAPI.models.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ShortStoryService implements IShortStoryService {

    @Override
    public ResponseEntity<ApiResponse> createNewShortStories() {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> deleteShortStory(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> updateShortStory(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> getAllShortStories() {
        return null;
    }
}
