package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.mapstruct.dtos.LearningRoomPostInstantDto;
import com.example.EnglishAppAPI.services.impls.LearningRoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RestControllerAdvice
@RequestMapping("${api.prefix}/learning-rooms")
@Validated
public class LearningRoomController {
    @Autowired
    private LearningRoomService learningRoomService;
    @PostMapping("/create-instantly")
    public ResponseEntity<?> createLearningRoomInstantly(@Valid @RequestBody LearningRoomPostInstantDto learningRoomPostInstantDto) {
        return learningRoomService.createLearningRoomInstantly(learningRoomPostInstantDto);
    }
}
