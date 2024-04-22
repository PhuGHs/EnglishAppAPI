package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.mapstruct.dtos.ChangePasswordDto;
import com.example.EnglishAppAPI.mapstruct.dtos.EmailVerificationDto;
import com.example.EnglishAppAPI.mapstruct.dtos.LoginDto;
import com.example.EnglishAppAPI.mapstruct.dtos.RegisterDto;
import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RestControllerAdvice
@RequestMapping("${api.prefix}/auth")
@Validated
public class AuthController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterDto registerDto) {
        return accountService.register(registerDto);
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginDto loginDto) {
        return accountService.login(loginDto);
    }

    @PutMapping("/forgot-password/{email}")
    public ResponseEntity<?> sendVerificationEmail(@PathVariable(name = "email") String email) {
        return accountService.sendVerificationEmail(email);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody EmailVerificationDto emailVerificationDto) {
        return accountService.verifyCode(emailVerificationDto);
    }

    @GetMapping("/current-user")
    public ResponseEntity<UserEntity> getCurrentUser() {
        return accountService.getCurrentUser();
    }
}
