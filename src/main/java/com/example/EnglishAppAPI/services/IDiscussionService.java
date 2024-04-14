package com.example.EnglishAppAPI.services;

import com.example.EnglishAppAPI.mapstruct.dtos.DiscussionDto;
import com.example.EnglishAppAPI.entities.Discussion;
import com.example.EnglishAppAPI.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface IDiscussionService {
    ResponseEntity<ApiResponse> getTopDiscussions();
    Page<Discussion> getAllDiscussions(int pageNumber, int pageSize, String sortBy);
    ResponseEntity<ApiResponse> addNewDiscussion(DiscussionDto discussion);
    ResponseEntity<ApiResponse> updateDiscussion(Long discussionId, DiscussionDto discussion);
    ResponseEntity<ApiResponse> deleteDiscussion(Long discussionId);

}
