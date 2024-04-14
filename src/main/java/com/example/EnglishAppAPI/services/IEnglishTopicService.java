package com.example.EnglishAppAPI.services;

import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTopicDto;
import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTopicQuestionDto;
import com.example.EnglishAppAPI.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface IEnglishTopicService {
    ResponseEntity<?> insertNewTopic(EnglishTopicDto englishTopic);
    ResponseEntity<ApiResponse> retrieveAllTopics();
    ResponseEntity<?> insertTopicQuestion(Long topicId, EnglishTopicQuestionDto englishTopicQuestionDto);
    ResponseEntity<ApiResponse> retrieveAllQuestionsOfATopic(Long topicId);
}
