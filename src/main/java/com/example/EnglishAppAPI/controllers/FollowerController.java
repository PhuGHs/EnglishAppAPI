package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.models.ApiResponse;
import com.example.EnglishAppAPI.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/followers")
public class FollowerController {
    @Autowired
    private UserService userService;
    @PostMapping("{currentUserId}/follow/{userIdToFollow}")
    public ResponseEntity<ApiResponse> followerUser(@PathVariable Long currentUserId, @PathVariable Long userIdToFollow) {
        return userService.followUsers(currentUserId, userIdToFollow);
    }

    @DeleteMapping("{currentUserId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<ApiResponse> unfollowUser(@PathVariable Long currentUserId, @PathVariable Long userIdToUnfollow) {
        return userService.unfollowUsers(currentUserId, userIdToUnfollow);
    }
}
