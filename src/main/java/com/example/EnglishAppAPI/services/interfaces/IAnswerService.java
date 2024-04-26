package com.example.EnglishAppAPI.services.interfaces;

import com.example.EnglishAppAPI.mapstruct.dtos.AnswerDto;
import com.example.EnglishAppAPI.mapstruct.dtos.AnswerPostDto;
import com.example.EnglishAppAPI.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface IAnswerService {
    Page<AnswerDto> getAllAnswers(Long discussionId, int pageNumber, int pageSize, String sortBy);
    ResponseEntity<ApiResponse> getAnswer(Long answerId);
    ResponseEntity<ApiResponse> createNewAnswer(AnswerPostDto answerDto);
    ResponseEntity<ApiResponse> editAnAnswer(Long id, AnswerPostDto answerDto);
    ResponseEntity<ApiResponse> deleteAnswer(Long answerId);
}
