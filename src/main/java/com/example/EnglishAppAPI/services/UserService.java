package com.example.EnglishAppAPI.services;

import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.responses.ApiResponseStatus;
import com.example.EnglishAppAPI.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public ResponseEntity<ApiResponse> followUsers(Long currentUserId, Long id) {
        UserEntity user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new NotFoundException("user is not authorized or not existed"));
        UserEntity followedUser = userRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("followedUser is not existed"));
        assert user != null;
        user.getFollowers().add(followedUser);
        followedUser.getFollowing().add(user);
        userRepository.save(user);
        userRepository.save(followedUser);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, "follow user", ""));
    }

    @Override
    public ResponseEntity<ApiResponse> unfollowUsers(Long currentUserId, Long id) {
        UserEntity user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new NotFoundException("user is not authorized or not existed"));
        UserEntity followedUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("unfollowedUser is not existed"));
        assert user != null;
        user.getFollowers().remove(followedUser);
        followedUser.getFollowing().remove(user);
        userRepository.save(user);
        userRepository.save(followedUser);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, "unfollow user", ""));
    }
}
