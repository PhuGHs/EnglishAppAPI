package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.dtos.DiscussionDto;
import com.example.EnglishAppAPI.entities.Discussion;
import com.example.EnglishAppAPI.models.ApiResponse;
import com.example.EnglishAppAPI.services.DiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RestControllerAdvice
@RequestMapping("${api.prefix}/discussions")
public class DiscussionController {
    @Autowired
    private DiscussionService discussionService;

    @PostMapping("/create-new-discussion")
    public ResponseEntity<ApiResponse> addNewDiscussion(@RequestBody DiscussionDto request) {
        return discussionService.addNewDiscussion(request);
    }

    @PutMapping("/{id}/edit-discussion")
    public ResponseEntity<ApiResponse> editADiscussion(@PathVariable Long id, @RequestBody DiscussionDto request) {
        return discussionService.updateDiscussion(id, request);
    }

    @GetMapping("")
    public Page<Discussion> getAllDiscussions(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortBy) {
        return discussionService.getAllDiscussions(pageNumber, pageSize, sortBy);
    }

    @GetMapping("/popular-discussions")
    public ResponseEntity<ApiResponse> getTopDiscussions() {
        return discussionService.getTopDiscussions();
    }
}
