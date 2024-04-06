package com.example.EnglishAppAPI.services;

import com.example.EnglishAppAPI.entities.Answer;
import com.example.EnglishAppAPI.models.ApiResponse;
import com.example.EnglishAppAPI.repositories.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AnswerService implements IAnswerService{
    @Autowired
    private AnswerRepository answerRepository;
    @Override
    public Page<Answer> getAllAnswers(Long discussionId, int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return answerRepository.findAll(pageable);
    }

    @Override
    public ResponseEntity<ApiResponse> createNewAnswer() {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> editAnAnswer() {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> deleteAnswer(Long answerId) {
        return null;
    }
}
