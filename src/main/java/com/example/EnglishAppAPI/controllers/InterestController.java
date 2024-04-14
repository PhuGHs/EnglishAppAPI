package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.entities.Interest;
import com.example.EnglishAppAPI.services.InterestService;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("${api.prefix}/interests")
public class InterestController {
    @Autowired
    private InterestService interestService;
    @PostMapping("/insert-new")
    public ResponseEntity<?> insertInterests(@NotEmpty @RequestBody String interestName) {
        return interestService.createNewInterest(interestName);
    }

    @PostMapping("/select-interests")
    public ResponseEntity<?> selectInterests(@RequestParam Long userId, @RequestBody Set<Interest> interests) {
        return interestService.selectInterests(userId, interests);
    }
}
