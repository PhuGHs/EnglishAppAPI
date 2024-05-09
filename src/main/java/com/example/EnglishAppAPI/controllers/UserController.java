package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.mapstruct.dtos.UserInformationDto;
import com.example.EnglishAppAPI.services.impls.SearchService;
import com.example.EnglishAppAPI.services.impls.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("${api.prefix}/users")
//@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final SearchService searchService;
    private final UserService userService;
    @Autowired
    public UserController(SearchService searchService, UserService userService) {
        this.searchService = searchService;
        this.userService = userService;
    }

    @GetMapping("/find-by-fullName")
    public ResponseEntity<?> findByFullName(@RequestParam String fullName) throws IOException {
        return searchService.fuzzySearchUserFullName(fullName);
    }

    @PutMapping("/${userId}change-info")
    public ResponseEntity<?> changeUserInfo(@PathVariable Long userId, @RequestBody UserInformationDto userInformationDto) {
        return userService.changeUserInformation(userId, userInformationDto);
    }

    @GetMapping("/{userId}/recommend-user-based-on-common-interests")
    public ResponseEntity<?> recommendUserBasedOnCommonInterests(@PathVariable Long userId) {
        return searchService.recommendUsersBasedOnCommonInterests(userId);
    }

    @GetMapping("/{userId}/recommend-user-based-on-englishLevel")
    public ResponseEntity<?> recommendUserBasedOnEnglishLevel(@PathVariable Long userId, @RequestParam int pageNumber, @RequestParam int pageSize) {
        return searchService.recommendUsersBasedOnEnglishLevel(userId, pageNumber, pageSize);
    }
}
