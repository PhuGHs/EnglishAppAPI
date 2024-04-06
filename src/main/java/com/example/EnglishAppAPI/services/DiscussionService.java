package com.example.EnglishAppAPI.services;

import com.example.EnglishAppAPI.dtos.DiscussionDto;
import com.example.EnglishAppAPI.entities.Discussion;
import com.example.EnglishAppAPI.entities.EnglishTopic;
import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.models.ApiResponse;
import com.example.EnglishAppAPI.repositories.DiscussionRepository;
import com.example.EnglishAppAPI.repositories.EnglishTopicRepository;
import com.example.EnglishAppAPI.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class DiscussionService implements IDiscussionService {
    private final DiscussionRepository discussionRepository;
    private final UserRepository userRepository;
    private EnglishTopicRepository englishTopicRepository;

    @Autowired
    public DiscussionService(DiscussionRepository discussionRepository, UserRepository userRepository) {
        this.discussionRepository = discussionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<ApiResponse> getTopDiscussions() {
        List<Discussion> discussions = discussionRepository.findTop5ByOrderByAnswersSizeDesc();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfully", "Get top 5 sucessfully", discussions));
    }

    @Override
    public Page<Discussion> getAllDiscussions(int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return discussionRepository.findAll(pageable);
    }

    @Override
    public ResponseEntity<ApiResponse> addNewDiscussion(DiscussionDto request) {
        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("Invalid user ID"));

        EnglishTopic topic = englishTopicRepository.findById(request.getEnglishTopicId())
                .orElseThrow(() -> new NotFoundException("Invalid topic ID"));

        Discussion discussion = Discussion.builder()
                .title(request.getTitle())
                .createdDate(new Date())
                .updatedDate(new Date())
                .user(user)
                .topic(topic)
                .answers(new HashSet<>())
                .build();
        discussionRepository.save(discussion);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Successfully", "Created a new discussion", discussion));
    }

    @Override
    public ResponseEntity<ApiResponse> updateDiscussion(Long discussionId, DiscussionDto discussion) {
        Discussion dis = discussionRepository.findById(discussionId)
                .orElseThrow(() -> new NotFoundException("Can't find the discussion"));
        EnglishTopic topic = englishTopicRepository.findById(discussion.getEnglishTopicId())
                .orElseThrow(() -> new NotFoundException("Can't find the topic with the provided id"));
        dis.setTopic(topic);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfully", "discussion was updated", dis));
    }

    @Override
    public ResponseEntity<ApiResponse> deleteDiscussion(Long discussionId) {
        discussionRepository.deleteById(discussionId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfully", "discussion was deleted", ""));
    }
}
