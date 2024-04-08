package com.example.EnglishAppAPI.services;

import com.example.EnglishAppAPI.dtos.ShortStoryDto;
import com.example.EnglishAppAPI.entities.ShortStory;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.models.ApiResponse;
import com.example.EnglishAppAPI.models.ApiResponseStatus;
import com.example.EnglishAppAPI.repositories.ShortStoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShortStoryService implements IShortStoryService {
    @Autowired
    private ShortStoryRepository shortStoryRepository;

    @Override
    public ResponseEntity<ApiResponse> createNewShortStories(ShortStoryDto shortStoryDto) {
        ShortStory shortStory = ShortStory.builder()
                .title(shortStoryDto.getTitle())
                .paragraph(shortStoryDto.getParagraph())
                .image(shortStoryDto.getImage())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ApiResponseStatus.SUCCESS, "create short story", shortStory));
    }

    @Override
    public ResponseEntity<?> deleteShortStory(Long id) {
        if (!shortStoryRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find the short story");
        }
        shortStoryRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Delete successfully");
    }

    @Override
    public ResponseEntity<ApiResponse> updateShortStory(Long id, ShortStoryDto shortStoryDto) {
        ShortStory shortStory = shortStoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("cannot find the short story"));
        shortStory.setTitle(shortStoryDto.getTitle());
        shortStory.setParagraph(shortStoryDto.getParagraph());
        shortStory.setImage(shortStoryDto.getImage());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, "Update a short story", shortStory));
    }

    @Override
    public ResponseEntity<ApiResponse> getAllShortStories() {
        List<ShortStory> shortStories = shortStoryRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, "Get all short stories", shortStories));
    }
}
