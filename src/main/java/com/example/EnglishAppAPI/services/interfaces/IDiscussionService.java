package com.example.EnglishAppAPI.services.interfaces;

import com.example.EnglishAppAPI.mapstruct.dtos.DiscussionDto;
import com.example.EnglishAppAPI.entities.Discussion;
import com.example.EnglishAppAPI.mapstruct.dtos.DiscussionPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.DiscussionTopicPostDto;
import com.example.EnglishAppAPI.mapstruct.enums.DiscussionOrderBy;
import com.example.EnglishAppAPI.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IDiscussionService {
    ResponseEntity<?> getDiscussion(Long id);
    ResponseEntity<ApiResponse> getTopDiscussions();
    Page<Discussion> getAllDiscussions(int pageNumber, int pageSize, String sortBy);
    ResponseEntity<ApiResponse> addNewDiscussion(DiscussionPostDto discussion);
    ResponseEntity<ApiResponse> updateDiscussion(Long discussionId, DiscussionPostDto discussion);
    ResponseEntity<ApiResponse> deleteDiscussion(Long discussionId);
    Page<DiscussionDto> getUserDiscussions(int pageNumber, int pageSize, DiscussionOrderBy sortBy, Long userId);
    Page<DiscussionDto> getDiscussionsByTopic(int pageNumber, int pageSize, DiscussionOrderBy sortBy, Long topicId);
    ResponseEntity<?> addTopic(DiscussionTopicPostDto dto);
    ResponseEntity<?> getAllTopics();
    ResponseEntity<?> filterDiscussion(List<String> options, String searchTerms, int pageNumber, int pageSize);
}
