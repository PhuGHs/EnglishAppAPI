package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.services.impls.EnglishLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/english-levels")
//@SecurityRequirement(name = "bearerAuth")
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
