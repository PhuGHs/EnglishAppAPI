package com.example.EnglishAppAPI.services.interfaces;

import com.example.EnglishAppAPI.mapstruct.dtos.UserInformationDto;
import com.example.EnglishAppAPI.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface IUserService {
    ResponseEntity<ApiResponse> followUsers(Long currentUserId, Long id);
    ResponseEntity<ApiResponse> unfollowUsers(Long currentUserId, Long id);
    ResponseEntity<ApiResponse> getUserInfo(Long id);
    ResponseEntity<?> changeUserInformation(Long currentUserId, UserInformationDto userInformationDto);
    ResponseEntity<?> getFollowers(Long currentUserId, int pageNumber, int pageSize, String sortBy);
    ResponseEntity<?> getFollowing(Long currentUserId, int pageNumber, int pageSize, String sortBy);
}
