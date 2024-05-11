package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTopicPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTopicQuestionPostDto;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.services.impls.EnglishTopicService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/english-topics")
@Validated
@SecurityRequirement(name = "bearerAuth")
public class EnglishTopicController {
    @Autowired
    private EnglishTopicService englishTopicService;

    @PostMapping("/insert-new-topic")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> insertNewTopic(@RequestBody @Valid EnglishTopicPostDto englishTopicPostDto) {
        return englishTopicService.insertNewTopic(englishTopicPostDto);
    }

    @PostMapping("/insert-new-question")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> insertNewTopicQuestion(@Valid @RequestBody EnglishTopicQuestionPostDto englishTopicQuestionDto) {
        return englishTopicService.insertTopicQuestion(englishTopicQuestionDto);
    }

    @GetMapping("/all-topics")
    public ResponseEntity<ApiResponse> retrieveAllTopics() {
        return englishTopicService.retrieveAllTopics();
    }

    @GetMapping("/all-topics-within-level/{levelId}")
    public ResponseEntity<ApiResponse> retrieveAllTopicsInLevel(@PathVariable @NotNull(message = "The levelId is required") Long levelId) {
        return englishTopicService.retrieveAllTopicsInLevel(levelId);
    }

    @GetMapping("{topicId}/all-questions")
    public ResponseEntity<ApiResponse> retrieveAllTopicQuestions(@PathVariable @NotNull(message = "The topicId is required") Long topicId) {
        return englishTopicService.retrieveAllQuestionsOfATopic(topicId);
    }
}
