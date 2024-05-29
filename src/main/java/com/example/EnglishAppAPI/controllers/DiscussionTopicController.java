package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.mapstruct.dtos.DiscussionPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.DiscussionTopicPostDto;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.services.impls.DiscussionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RestControllerAdvice
@RequestMapping("${api.prefix}/discussion-topics")
@SecurityRequirement(name = "bearerAuth")
public class DiscussionTopicController {
    @Autowired
    private DiscussionService discussionService;

    @PostMapping("/create-new-topic")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addNewDiscussion(@RequestBody DiscussionTopicPostDto request) {
        return discussionService.addTopic(request);
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return discussionService.getAllTopics();
    }
}
