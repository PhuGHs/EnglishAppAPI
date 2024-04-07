package com.example.EnglishAppAPI.services;

import com.example.EnglishAppAPI.dtos.EnglishTopicDto;
import com.example.EnglishAppAPI.dtos.EnglishTopicQuestionDto;
import com.example.EnglishAppAPI.models.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public interface IEnglishTopicService {
    ResponseEntity<?> insertNewTopic(EnglishTopicDto englishTopic);
    ResponseEntity<ApiResponse> retrieveAllTopics();
    ResponseEntity<?> insertTopicQuestion(Long topicId, EnglishTopicQuestionDto englishTopicQuestionDto);
    ResponseEntity<ApiResponse> retrieveAllQuestionsOfATopic(Long topicId);
}
