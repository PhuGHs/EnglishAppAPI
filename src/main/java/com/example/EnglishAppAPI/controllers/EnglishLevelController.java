package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.services.impls.EnglishLevelService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/english-levels")
@SecurityRequirement(name = "bearerAuth")
@Slf4j
public class EnglishLevelController {
    @Autowired
    private EnglishLevelService englishLevelService;

    @GetMapping("/user-who-has-same-level")
    public ResponseEntity<?> getUsersWhoHaveTheSameLevel() {
        return englishLevelService.getUsersWhoHasTheSameLevelAsCurrentUser();
    }

    @PostMapping("/insert-levels")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> insertLevels() {
        return englishLevelService.insertLevels();
    }
}
