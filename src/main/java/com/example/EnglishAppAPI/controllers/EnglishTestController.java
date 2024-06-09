package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTestPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.QuestionPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.SubmitTestDto;
import com.example.EnglishAppAPI.services.impls.EnglishTestService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/english-tests")
@RestControllerAdvice
@SecurityRequirement(name = "bearerAuth")
@Validated
public class EnglishTestController {
    @Autowired
    private EnglishTestService englishTestService;

    @GetMapping("")
    public ResponseEntity<?> getEnglishTests(@RequestParam Long levelId) {
        return englishTestService.getEnglishTests(levelId);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createEnglishTest(@RequestBody @Valid EnglishTestPostDto englishTestPostDto) {
        return englishTestService.createEnglishTest(englishTestPostDto);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteEnglishTest(@RequestParam Long id) {
        return englishTestService.deleteEnglishTest(id);
    }

    @GetMapping("/{testId}/get-questions")
    public ResponseEntity<?> getQuestions(@PathVariable Long testId) {
        return englishTestService.getQuestions(testId);
    }

    @GetMapping("/get-user-tests")
    public ResponseEntity<?> getUserTests(@RequestParam Long userId, @RequestParam Long levelId) {
        return englishTestService.getUserTests(userId, levelId);
    }

    @PostMapping("/insert-questions")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> insertQuestionsToEnglishTest(@RequestBody @Valid QuestionPostDto questionPostDto) {
        return englishTestService.insertQuestionToEnglishTest(questionPostDto);
    }

    @PostMapping("/submit-test")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> takeEnglishTest(@RequestBody SubmitTestDto submitTestDto) {
        return englishTestService.submitTest(submitTestDto);
    }
}
