package com.example.EnglishAppAPI.services;

import com.example.EnglishAppAPI.entities.Answer;
import com.example.EnglishAppAPI.mapstruct.dtos.DiscussionDto;
import com.example.EnglishAppAPI.entities.Discussion;
import com.example.EnglishAppAPI.entities.EnglishTopic;
import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.mapstruct.dtos.DiscussionPostDto;
import com.example.EnglishAppAPI.mapstruct.enums.DiscussionOrderBy;
import com.example.EnglishAppAPI.mapstruct.mappers.DiscussionMapper;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.responses.ApiResponseStatus;
import com.example.EnglishAppAPI.repositories.DiscussionRepository;
import com.example.EnglishAppAPI.repositories.EnglishTopicRepository;
import com.example.EnglishAppAPI.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class DiscussionService implements IDiscussionService {
    private final DiscussionRepository discussionRepository;
    private final UserRepository userRepository;
    private final EnglishTopicRepository englishTopicRepository;
    private final DiscussionMapper discussionMapper;

    @Autowired
    public DiscussionService(DiscussionRepository discussionRepository, UserRepository userRepository, EnglishTopicRepository englishTopicRepository, DiscussionMapper discussionMapper) {
        this.discussionRepository = discussionRepository;
        this.userRepository = userRepository;
        this.englishTopicRepository = englishTopicRepository;
        this.discussionMapper = discussionMapper;
    }

    @Override
    public ResponseEntity<ApiResponse> getTopDiscussions() {
        List<Discussion> discussions = discussionRepository.findTop5ByOrderByCreatedDateDesc();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, "Get top 5 sucessfully", discussions.stream().map(discussionMapper::toDto)));
    }

    @Override
    public Page<Discussion> getAllDiscussions(int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return discussionRepository.findAll(pageable);
    }

    @Override
    public ResponseEntity<ApiResponse> addNewDiscussion(DiscussionPostDto request) {
        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("Invalid user ID"));

        EnglishTopic topic = englishTopicRepository.findById(request.getEnglishTopicId())
                .orElseThrow(() -> new NotFoundException("Invalid topic ID"));

        Discussion discussion = Discussion.builder()
                .title(request.getTitle())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .user(user)
                .topic(topic)
                .answers(new HashSet<>())
                .build();
        discussionRepository.save(discussion);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ApiResponseStatus.SUCCESS, "Created a new discussion", discussionMapper.toDto(discussion)));
    }

    @Override
    public ResponseEntity<ApiResponse> updateDiscussion(Long discussionId, DiscussionPostDto discussion) {
        UserEntity user = userRepository.findById(discussion.getUserId())
                .orElseThrow(() -> new NotFoundException("Can't find the user"));
        Discussion dis = discussionRepository.findById(discussionId)
                .orElseThrow(() -> new NotFoundException("Can't find the discussion"));
        if (!user.getUserId().equals(dis.getUser().getUserId())) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ApiResponse(ApiResponseStatus.FAIL, "u dont own the discussion", ""));
        }
        EnglishTopic topic = englishTopicRepository.findById(discussion.getEnglishTopicId())
                .orElseThrow(() -> new NotFoundException("Can't find the topic with the provided id"));
        dis.setTopic(topic);
        dis.setTitle(discussion.getTitle());
        discussionRepository.save(dis);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, "discussion was updated", discussionMapper.toDto(dis)));
    }

    @Override
    public ResponseEntity<ApiResponse> deleteDiscussion(Long discussionId) {
        discussionRepository.deleteById(discussionId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, "discussion was deleted", ""));
    }

    @Override
    public Page<DiscussionDto> getUserDiscussions(int pageNumber, int pageSize, DiscussionOrderBy sortBy, Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Can't find the user"));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortBy == DiscussionOrderBy.id ? Sort.by(sortBy.toString()).ascending() : Sort.by(sortBy.toString()).descending());
        Page<Discussion> discussionsPage = discussionRepository.findByUser(user, pageable);
        return discussionsPage.map(discussionMapper::toDto);
    }

    @Override
    public Page<DiscussionDto> getDiscussionsByTopic(int pageNumber, int pageSize, DiscussionOrderBy sortBy, Long topicId) {
        EnglishTopic topic = englishTopicRepository.findById(topicId)
                .orElseThrow(() -> new NotFoundException("Can't find the topic with the provided id"));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortBy == DiscussionOrderBy.id ? Sort.by(sortBy.toString()).ascending() : Sort.by(sortBy.toString()).descending());
        Page<Discussion> discussionPage = discussionRepository.findByTopic(topic, pageable);
        return discussionPage.map(discussionMapper::toDto);
    }
}
