package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.mapstruct.dtos.ShortStoryPostDto;
import com.example.EnglishAppAPI.mapstruct.enums.ShortStoryOrderBy;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.services.impls.ShortStoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/short-stories")
@SecurityRequirement(name = "bearerAuth")
public class ShortStoryController {
    @Autowired
    private ShortStoryService shortStoryService;

    @PostMapping("/create-new-short-story")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> createNewShortStories(@RequestBody ShortStoryPostDto shortStoryPostDto) {
        return shortStoryService.createNewShortStories(shortStoryPostDto);
    }

    @PutMapping("{id}/update-short-story")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> updateShortStory(@PathVariable Long id, @RequestBody ShortStoryPostDto shortStoryPostDto) {
        return shortStoryService.updateShortStory(id, shortStoryPostDto);
    }

    @PutMapping("{id}/like-story")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<ApiResponse> likeStory(@PathVariable @NotNull(message = "the id is required") Long id) {
        return shortStoryService.likeStory(id);
    }

    @DeleteMapping("{id}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
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
