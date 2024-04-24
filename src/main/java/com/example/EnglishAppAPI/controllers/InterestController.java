package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.entities.Interest;
import com.example.EnglishAppAPI.mapstruct.dtos.InterestPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.InterestPutDto;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.services.InterestService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("${api.prefix}/interests")
//@SecurityRequirement(name = "bearerAuth")
public class InterestController {
    @Autowired
    private InterestService interestService;
    @PostMapping("/insert-new")
    public ResponseEntity<?> insertInterests(@RequestBody InterestPostDto interestPostDto) {
        return interestService.createNewInterest(interestPostDto);
    }

    @PostMapping("/select-interests")
    public ResponseEntity<?> selectInterests(@RequestBody InterestPutDto interestPutDto) {
        return interestService.selectInterests(interestPutDto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserInterests(@PathVariable @NotNull Long userId) {
        return interestService.getUserInterests(userId);
    }
}
