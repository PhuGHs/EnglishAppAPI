package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.mapstruct.dtos.AnswerDto;
import com.example.EnglishAppAPI.entities.Answer;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.services.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RestControllerAdvice
@RequestMapping("${api.prefix}/answers")
public class AnswerController {
    @Autowired
    private AnswerService answerService;
    @GetMapping("")
    public Page<Answer> getAllAnswers(@RequestParam Long discussionId, @RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam(defaultValue = "createdDate") String sortBy) {
        return answerService.getAllAnswers(discussionId, pageNumber, pageSize, sortBy);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createNewAnswer(@RequestBody AnswerDto answerDto) {
        return answerService.createNewAnswer(answerDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> editAnAnswer(@PathVariable Long id, @RequestBody AnswerDto answerDto) {
        return answerService.editAnAnswer(id, answerDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteAnAnswer(@PathVariable Long id) {
        return answerService.deleteAnswer(id);
    }
}
