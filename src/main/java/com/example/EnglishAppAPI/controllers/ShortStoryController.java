package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.mapstruct.dtos.ShortStoryPostDto;
import com.example.EnglishAppAPI.mapstruct.enums.ShortStoryOrderBy;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.services.impls.ShortStoryService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/short-stories")
//@SecurityRequirement(name = "bearerAuth")
public class ShortStoryController {
    @Autowired
    private ShortStoryService shortStoryService;
    @PostMapping("/create-new-short-story")
    public ResponseEntity<ApiResponse> createNewShortStories(@RequestBody ShortStoryPostDto shortStoryPostDto) {
        return shortStoryService.createNewShortStories(shortStoryPostDto);
    }

    @PutMapping("{id}/update-short-story")
    public ResponseEntity<ApiResponse> updateShortStory(@PathVariable Long id, @RequestBody ShortStoryPostDto shortStoryPostDto) {
        return shortStoryService.updateShortStory(id, shortStoryPostDto);
    }

    @PutMapping("{id}/like-story")
    public ResponseEntity<ApiResponse> likeStory(@PathVariable @NotNull(message = "the id is required") Long id) {
        return shortStoryService.likeStory(id);
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<?> deleteShortStory(@PathVariable Long id) {
        return shortStoryService.deleteShortStory(id);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> getShortStories(
            @RequestParam int pageNumber,
            @RequestParam int pageSize,
            @RequestParam(defaultValue = "numberOfLikes") ShortStoryOrderBy sortBy
    ) {
        return shortStoryService.getAllShortStories(pageNumber, pageSize, sortBy);
    }
}
