package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.mapstruct.dtos.UserInformationDto;
import com.example.EnglishAppAPI.services.impls.SearchService;
import com.example.EnglishAppAPI.services.impls.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("${api.prefix}/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final SearchService searchService;
    private final UserService userService;
    @Autowired
    public UserController(SearchService searchService, UserService userService) {
        this.searchService = searchService;
        this.userService = userService;
    }

    @GetMapping("/find-by-fullName")
    public ResponseEntity<?> findByFullName(@RequestParam String fullName, @RequestParam Long currentUserId) throws IOException {
        return searchService.fuzzySearchUserFullName(fullName, currentUserId);
    }

    @PutMapping("/{userId}/change-info")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> changeUserInfo(@PathVariable Long userId, @RequestBody UserInformationDto userInformationDto) {
        return userService.changeUserInformation(userId, userInformationDto);
    }

    @GetMapping("/{userId}/recommend-user-based-on-common-interests")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> recommendUserBasedOnCommonInterests(@PathVariable Long userId, @RequestParam("page") int page, @RequestParam("size") int size ) throws IOException {
        return searchService.recommendUsersBasedOnCommonInterests(userId, page, size);
    }

    @GetMapping("/{userId}/recommend-user-based-on-englishLevel")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> recommendUserBasedOnEnglishLevel(@PathVariable Long userId, @RequestParam int pageNumber, @RequestParam int pageSize) {
        return searchService.recommendUsersBasedOnEnglishLevel(userId, pageNumber, pageSize);
    }

    @GetMapping("/{userId}/get-user-profile")
    public ResponseEntity<?> getUserProfile(@PathVariable Long userId) {
        return userService.getUserInfo(userId);
    }
}
