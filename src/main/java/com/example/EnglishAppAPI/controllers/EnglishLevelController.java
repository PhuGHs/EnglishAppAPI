package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.services.EnglishLevelService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/english-levels")
@SecurityRequirement(name = "bearerAuth")
public class EnglishLevelController {
    @Autowired
    private EnglishLevelService englishLevelService;

    @GetMapping("/user-who-has-same-level")
    public ResponseEntity<?> getUsersWhoHaveTheSameLevel() {
        return englishLevelService.getUsersWhoHasTheSameLevelAsCurrentUser();
    }

    @PostMapping("/insert-levels")
    public ResponseEntity<?> insertLevels() {
        return englishLevelService.insertLevels();
    }
}
