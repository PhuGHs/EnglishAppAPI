package com.example.EnglishAppAPI.services;

import com.example.EnglishAppAPI.mapstruct.dtos.AnswerDto;
import com.example.EnglishAppAPI.entities.Answer;
import com.example.EnglishAppAPI.entities.Discussion;
import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.mapstruct.dtos.AnswerGetDto;
import com.example.EnglishAppAPI.mapstruct.mappers.MapStructMapper;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.responses.ApiResponseStatus;
import com.example.EnglishAppAPI.repositories.AnswerRepository;
import com.example.EnglishAppAPI.repositories.DiscussionRepository;
import com.example.EnglishAppAPI.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class AnswerService implements IAnswerService {
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final DiscussionRepository discussionRepository;
    private final MapStructMapper mapper;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, UserRepository userRepository, DiscussionRepository discussionRepository, MapStructMapper mapper) {
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.discussionRepository = discussionRepository;
        this.mapper = mapper;
    }

    @Override
    public Page<AnswerGetDto> getAllAnswers(Long discussionId, int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Answer> answersPage = answerRepository.findAll(pageable);
        return answersPage.map(mapper::answerToDto);
    }

    @Override
    public ResponseEntity<ApiResponse> getAnswer(Long answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new NotFoundException("the answer have been deleted or not existed"));
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get answer", mapper.answerToDto(answer)));
    }

    @Override
    public ResponseEntity<ApiResponse> createNewAnswer(AnswerDto answerDto) {
        UserEntity user = userRepository.findById(answerDto.getUserId())
                .orElseThrow(() -> new NotFoundException("User is not found"));
        Discussion discussion = discussionRepository.findById(answerDto.getDiscussionId())
                .orElseThrow(() -> new NotFoundException("Discussion Id is invalid"));
        Answer answer = Answer.builder()
                .discussion(discussion)
                .user(user)
                .answerText(answerDto.getAnswerText())
                .build();
        AnswerGetDto answerGetDto = mapper.answerToDto(answer);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ApiResponseStatus.SUCCESS, "Answered the discussion successfully", answerGetDto));
    }

    @Override
    public ResponseEntity<ApiResponse> editAnAnswer(Long id, AnswerDto answerDto) {
        if (discussionRepository.existsById(answerDto.getDiscussionId())) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ApiResponseStatus.SUCCESS, "the discussion id is not found", ""));
        }
        if (userRepository.existsById(answerDto.getUserId())) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ApiResponseStatus.SUCCESS, "the user id is not found", ""));
        }
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("answer is not found"));
        answer.setAnswerText(answer.getAnswerText());
        answer.setUpdatedAt(LocalDateTime.now());
        AnswerGetDto answerGetDto = mapper.answerToDto(answer);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ApiResponseStatus.SUCCESS, "Edited the answer successfully", answerGetDto));
    }

    @Override
    public ResponseEntity<ApiResponse> deleteAnswer(Long answerId) {
        if(!answerRepository.existsById(answerId)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ApiResponseStatus.FAIL, "the id is not found", ""));
        }
        answerRepository.deleteById(answerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ApiResponseStatus.SUCCESS, "delete successfully", ""));
    }
}
