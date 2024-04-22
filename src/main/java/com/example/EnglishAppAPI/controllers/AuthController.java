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

    @PostMapping("/register-admin")
    public ResponseEntity<ApiResponse> registerAdminAccount(@Valid @RequestBody RegisterDto registerDto) {
        return accountService.registerAdminAccount(registerDto);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginDto loginDto) {
        return accountService.login(loginDto);
    }

    @PutMapping("/forgot-password")
    public ResponseEntity<?> sendVerificationEmail(@RequestParam(name = "email") String email) {
        return accountService.sendVerificationEmail(email);
    }

    @PutMapping("/verify-code")
    public ResponseEntity<?> verifyCode(@Valid @RequestBody EmailVerificationDto emailVerificationDto) {
        return accountService.verifyCode(emailVerificationDto);
    }

    @PutMapping("/reset-password-authenticated")
    public ResponseEntity<String> resetPasswordAuthenticated(@Valid @RequestBody LoginDto loginDto) {
        return accountService.resetPasswordWhenLoggedIn(loginDto);
    }

    @PutMapping("/reset-password-forgot")
    public ResponseEntity<String> resetPasswordWhenForgot(@Valid @RequestBody LoginDto loginDto) {
        return accountService.resetPasswordWhenForget(loginDto);
    }

    @GetMapping("/current-user")
    public ResponseEntity<UserEntity> getCurrentUser() {
        return accountService.getCurrentUser();
    }
}
