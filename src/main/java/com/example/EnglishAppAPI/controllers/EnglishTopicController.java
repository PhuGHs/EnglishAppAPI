package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTopicDto;
import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTopicQuestionDto;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.services.EnglishTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/english-topics")
public class EnglishTopicController {
    @Autowired
    private EnglishTopicService englishTopicService;

    @PostMapping("/insert-new-topic")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> insertNewTopic(@RequestBody  EnglishTopicDto englishTopicDto) {
        return englishTopicService.insertNewTopic(englishTopicDto);
    }

    @PostMapping("/insert-new-question")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> insertNewTopicQuestion(@RequestParam Long id, @RequestBody EnglishTopicQuestionDto englishTopicQuestionDto) {
        return englishTopicService.insertTopicQuestion(id, englishTopicQuestionDto);
    }

    @GetMapping("/all-topics")
    @PreAuthorize("hasAnyRole('LEARNER', 'ADMIN')")
    public ResponseEntity<ApiResponse> retrieveAllTopics() {
        return englishTopicService.retrieveAllTopics();
    }

    @GetMapping("{topicId}/all-questions")
    @PreAuthorize("hasAnyRole('LEARNER', 'ADMIN')")
    public ResponseEntity<ApiResponse> retrieveAllTopicQuestions(@PathVariable Long topicId) {
        return englishTopicService.retrieveAllQuestionsOfATopic(topicId);
    }
}
