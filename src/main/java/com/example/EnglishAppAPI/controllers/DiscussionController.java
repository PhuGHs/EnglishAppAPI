package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.mapstruct.dtos.DiscussionDto;
import com.example.EnglishAppAPI.entities.Discussion;
import com.example.EnglishAppAPI.mapstruct.dtos.DiscussionPostDto;
import com.example.EnglishAppAPI.mapstruct.enums.DiscussionOrderBy;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.services.DiscussionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RestControllerAdvice
@RequestMapping("${api.prefix}/discussions")
//@SecurityRequirement(name = "bearerAuth")
public class DiscussionController {
    @Autowired
    private DiscussionService discussionService;

    @PostMapping("/create-new-discussion")
    public ResponseEntity<ApiResponse> addNewDiscussion(@RequestBody DiscussionPostDto request) {
        return discussionService.addNewDiscussion(request);
    }

    @PutMapping("/{id}/edit-discussion")
    public ResponseEntity<ApiResponse> editADiscussion(@PathVariable Long id, @RequestBody DiscussionPostDto request) {
        return discussionService.updateDiscussion(id, request);
    }

    @GetMapping("")
    public Page<Discussion> getAllDiscussions(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortBy) {
        return discussionService.getAllDiscussions(pageNumber, pageSize, sortBy);
    }

    @GetMapping("/user")
    public Page<DiscussionDto> getUserDiscussions(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize, @RequestParam DiscussionOrderBy sortBy) {
        return discussionService.getUserDiscussions(pageNumber, pageSize, sortBy);
    }

    @GetMapping("/popular-discussions")
    public ResponseEntity<ApiResponse> getTopDiscussions() {
        return discussionService.getTopDiscussions();
    }

    @DeleteMapping("{discussionId}/delete")
    public ResponseEntity<ApiResponse> deleteDiscussion(@PathVariable @NotNull(message = "discussionId must be not null") Long discussionId) {
        return discussionService.deleteDiscussion(discussionId);
    }

}
