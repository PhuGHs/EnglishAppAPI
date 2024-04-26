package com.example.EnglishAppAPI.services.interfaces;

import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTopicDto;
import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTopicPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTopicQuestionDto;
import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTopicQuestionPostDto;
import com.example.EnglishAppAPI.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface IEnglishTopicService {
    ResponseEntity<?> insertNewTopic(EnglishTopicPostDto englishTopic);
    ResponseEntity<ApiResponse> retrieveAllTopics();
    ResponseEntity<ApiResponse> retrieveAllTopicsInLevel(Long levelId);
    ResponseEntity<?> insertTopicQuestion(EnglishTopicQuestionPostDto englishTopicQuestionDto);
    ResponseEntity<ApiResponse> retrieveAllQuestionsOfATopic(Long topicId);
}
