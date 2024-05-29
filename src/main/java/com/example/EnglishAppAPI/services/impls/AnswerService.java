package com.example.EnglishAppAPI.services.impls;

import com.example.EnglishAppAPI.entities.Answer;
import com.example.EnglishAppAPI.entities.Discussion;
import com.example.EnglishAppAPI.entities.Notification;
import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.entities.indexes.DiscussionDocument;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.mapstruct.dtos.AnswerDto;
import com.example.EnglishAppAPI.mapstruct.dtos.AnswerPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.NotificationDto;
import com.example.EnglishAppAPI.mapstruct.dtos.NotificationPostDto;
import com.example.EnglishAppAPI.mapstruct.enums.NotificationType;
import com.example.EnglishAppAPI.mapstruct.mappers.AnswerMapper;
import com.example.EnglishAppAPI.repositories.elas.DiscussionDocumentRepository;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.responses.ApiResponseStatus;
import com.example.EnglishAppAPI.repositories.AnswerRepository;
import com.example.EnglishAppAPI.repositories.DiscussionRepository;
import com.example.EnglishAppAPI.repositories.UserRepository;
import com.example.EnglishAppAPI.services.interfaces.IAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class AnswerService implements IAnswerService {
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final DiscussionRepository discussionRepository;
    private final AnswerMapper mapper;
    private final NotificationService notificationService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final DiscussionDocumentRepository discussionDocumentRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, UserRepository userRepository, DiscussionRepository discussionRepository, AnswerMapper mapper, NotificationService notificationService, SimpMessagingTemplate simpMessagingTemplate, DiscussionDocumentRepository discussionDocumentRepository) {
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.discussionRepository = discussionRepository;
        this.mapper = mapper;
        this.notificationService = notificationService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.discussionDocumentRepository = discussionDocumentRepository;
    }

    @Override
    public Page<AnswerDto> getAllAnswers(Long discussionId, int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        Page<Answer> answersPage = answerRepository.findAll(pageable);
        return answersPage.map(mapper::toDto);
    }

    @Override
    public ResponseEntity<ApiResponse> getAnswer(Long answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new NotFoundException("the answer have been deleted or not existed"));
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get answer", mapper.toDto(answer)));
    }

    @Override
    public ResponseEntity<ApiResponse> createNewAnswer(AnswerPostDto answerDto) {
        UserEntity user = userRepository.findById(answerDto.getUserId())
                .orElseThrow(() -> new NotFoundException("User is not found"));
        Discussion discussion = discussionRepository.findById(answerDto.getDiscussionId())
                .orElseThrow(() -> new NotFoundException("Discussion Id is invalid"));
        discussion.setNumberOfAnswers(discussion.getNumberOfAnswers() + 1);
        Answer answer = Answer.builder()
                .discussion(discussion)
                .user(user)
                .answerText(answerDto.getAnswerText())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();

        Answer answer1 = answerRepository.save(answer);
        AnswerDto answerGetDto = mapper.toDto(answer1);
        discussion = discussionRepository.save(discussion);
        updateDocument(discussion);
        NotificationDto notification = notificationService.addNotification(new NotificationPostDto(answerDto.getUserId(), discussion.getUser().getUserId(), user.getFullName() + "commented on your discussion", false, NotificationType.answer ,answer1.getAnswerId(), discussion.getId()));
        simpMessagingTemplate.convertAndSend("/user/"+ notification.getReceiver().getUserId(), notification);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ApiResponseStatus.SUCCESS, "Answered the discussion successfully", answerGetDto));
    }

    @Override
    public ResponseEntity<ApiResponse> editAnAnswer(Long id, AnswerPostDto answerDto) {
        if (discussionRepository.existsById(answerDto.getDiscussionId())) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ApiResponseStatus.SUCCESS, "the discussion id is not found", ""));
        }
        if (userRepository.existsById(answerDto.getUserId())) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ApiResponseStatus.SUCCESS, "the user id is not found", ""));
        }
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("answer is not found"));
        answer.setAnswerText(answer.getAnswerText());
        answer.setUpdatedAt(new Date());
        AnswerDto answerGetDto = mapper.toDto(answer);
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

    private void updateDocument(Discussion discussion) {
        Optional<DiscussionDocument> discussionDocument = discussionDocumentRepository.findById(discussion.getId());
        if (discussionDocument.isPresent()) {
            DiscussionDocument discussionDoc = discussionDocument.get();
            discussionDoc.setTitle(discussion.getTitle());
            discussionDoc.setCreatedDate(discussion.getCreatedDate());
            discussionDoc.setUpdatedDate(new Date());
            discussionDoc.setNumberOfAnswers(discussion.getNumberOfAnswers());
        }
        discussionRepository.save(discussion);
    }
}
