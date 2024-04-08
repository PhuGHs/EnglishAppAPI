package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.dtos.ShortStoryDto;
import com.example.EnglishAppAPI.models.ApiResponse;
import com.example.EnglishAppAPI.services.ShortStoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/short-stories")
public class ShortStoryController {
    @Autowired
    private ShortStoryService shortStoryService;
    @PostMapping("/create-new-short-story")
    public ResponseEntity<ApiResponse> createNewShortStories(@RequestBody ShortStoryDto shortStoryDto) {
        return shortStoryService.createNewShortStories(shortStoryDto);
    }

    @PutMapping("{id}/update-short-story")
    public ResponseEntity<ApiResponse> updateShortStory(@PathVariable Long id, @RequestBody ShortStoryDto shortStoryDto) {
        return shortStoryService.updateShortStory(id, shortStoryDto);
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<?> deleteShortStory(@PathVariable Long id) {
        return shortStoryService.deleteShortStory(id);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> getShortStories() {
        return shortStoryService.getAllShortStories();
    }
}
