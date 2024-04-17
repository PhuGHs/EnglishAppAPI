package com.example.EnglishAppAPI.services;

import com.example.EnglishAppAPI.mapstruct.dtos.AnswerDto;
import com.example.EnglishAppAPI.entities.Answer;
import com.example.EnglishAppAPI.mapstruct.dtos.AnswerGetDto;
import com.example.EnglishAppAPI.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface IAnswerService {
    Page<AnswerGetDto> getAllAnswers(Long discussionId, int pageNumber, int pageSize, String sortBy);
    ResponseEntity<ApiResponse> getAnswer(Long answerId);
    ResponseEntity<ApiResponse> createNewAnswer(AnswerDto answerDto);
    ResponseEntity<ApiResponse> editAnAnswer(Long id, AnswerDto answerDto);
    ResponseEntity<ApiResponse> deleteAnswer(Long answerId);
}
