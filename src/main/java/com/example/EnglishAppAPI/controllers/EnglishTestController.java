package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTestPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.QuestionPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.SubmitTestDto;
import com.example.EnglishAppAPI.services.impls.EnglishTestService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/english-tests")
@RestControllerAdvice
@Validated
public class EnglishTestController {
    @Autowired
    private EnglishTestService englishTestService;

    @GetMapping("")
    public ResponseEntity<?> getEnglishTests() {
        return englishTestService.getEnglishTests();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createEnglishTest(@RequestBody @Valid EnglishTestPostDto englishTestPostDto) {
        return englishTestService.createEnglishTest(englishTestPostDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteEnglishTest(@RequestParam Long id) {
        return englishTestService.deleteEnglishTest(id);
    }

    @GetMapping("/{testId}/get-questions")
    public ResponseEntity<?> getQuestions(@PathVariable Long testId) {
        return englishTestService.getQuestions(testId);
    }

    @PostMapping("/insert-questions")
    public ResponseEntity<?> insertQuestionsToEnglishTest(@RequestBody @Valid QuestionPostDto questionPostDto) {
        return englishTestService.insertQuestionToEnglishTest(questionPostDto);
    }

    @PostMapping("/submit-test")
    public ResponseEntity<?> takeEnglishTest(@RequestBody SubmitTestDto submitTestDto) {
        return englishTestService.submitTest(submitTestDto);
    }
}
