package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.services.impls.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/followers")
@SecurityRequirement(name = "bearerAuth")
public class FollowerController {
    @Autowired
    private UserService userService;
    @PostMapping("{currentUserId}/follow/{userIdToFollow}")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<ApiResponse> followerUser(@PathVariable Long currentUserId, @PathVariable Long userIdToFollow) {
        return userService.followUsers(currentUserId, userIdToFollow);
    }

    @DeleteMapping("{currentUserId}/unfollow/{userIdToUnfollow}")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<ApiResponse> unfollowUser(@PathVariable Long currentUserId, @PathVariable Long userIdToUnfollow) {
        return userService.unfollowUsers(currentUserId, userIdToUnfollow);
    }
}
