package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.mapstruct.dtos.AnswerDto;
import com.example.EnglishAppAPI.mapstruct.dtos.AnswerPostDto;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.services.impls.AnswerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RestControllerAdvice
@RequestMapping("${api.prefix}/answers")
@SecurityRequirement(name = "bearerAuth")
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @GetMapping("/get-by-discussion")
    public Page<AnswerDto> getAllAnswers(@RequestParam Long discussionId, @RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam(defaultValue = "createdAt") String sortBy) {
        return answerService.getAllAnswers(discussionId, pageNumber, pageSize, sortBy);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<ApiResponse> createNewAnswer(@RequestBody AnswerPostDto answerDto) {
        return answerService.createNewAnswer(answerDto);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<ApiResponse> editAnAnswer(@PathVariable Long id, @RequestBody AnswerPostDto answerDto) {
        return answerService.editAnAnswer(id, answerDto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<ApiResponse> deleteAnAnswer(@PathVariable Long id) {
        return answerService.deleteAnswer(id);
    }
}
