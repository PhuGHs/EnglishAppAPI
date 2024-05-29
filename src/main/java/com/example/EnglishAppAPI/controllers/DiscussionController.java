package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.mapstruct.dtos.DiscussionDto;
import com.example.EnglishAppAPI.entities.Discussion;
import com.example.EnglishAppAPI.mapstruct.dtos.DiscussionPostDto;
import com.example.EnglishAppAPI.mapstruct.enums.DiscussionOrderBy;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.services.impls.DiscussionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RestControllerAdvice
@RequestMapping("${api.prefix}/discussions")
@SecurityRequirement(name = "bearerAuth")
public class DiscussionController {
    @Autowired
    private DiscussionService discussionService;

    @PostMapping("/create-new-discussion")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<ApiResponse> addNewDiscussion(@RequestBody DiscussionPostDto request) {
        return discussionService.addNewDiscussion(request);
    }

    @PutMapping("/{id}/edit-discussion")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<ApiResponse> editADiscussion(@PathVariable Long id, @RequestBody DiscussionPostDto request) {
        return discussionService.updateDiscussion(id, request);
    }

    @GetMapping("")
    public Page<Discussion> getAllDiscussions(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortBy) {
        return discussionService.getAllDiscussions(pageNumber, pageSize, sortBy);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('LEARNER')")
    public Page<DiscussionDto> getUserDiscussions(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam DiscussionOrderBy sortBy,
            @PathVariable @NotNull(message = "this user id is required") Long userId) {
        return discussionService.getUserDiscussions(pageNumber, pageSize, sortBy, userId);
    }

    @GetMapping("/{userId}/popular-discussions")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<ApiResponse> getTopDiscussions(@PathVariable Long userId) {
        return discussionService.getTopDiscussions();
    }

    @GetMapping("/{topicId}")
    @PreAuthorize("hasAuthority('LEARNER')")
    public Page<DiscussionDto> getDiscussionsByTopic(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam DiscussionOrderBy sortBy,
            @PathVariable Long topicId
    ) {
        return discussionService.getDiscussionsByTopic(pageNumber, pageSize, sortBy, topicId);
    }

    @DeleteMapping("{discussionId}/delete")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<ApiResponse> deleteDiscussion(@PathVariable @NotNull(message = "discussionId must be not null") Long discussionId) {
        return discussionService.deleteDiscussion(discussionId);
    }

}
