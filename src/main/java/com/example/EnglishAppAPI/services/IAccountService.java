package com.example.EnglishAppAPI.services;

import com.example.EnglishAppAPI.mapstruct.dtos.EmailVerificationDto;
import com.example.EnglishAppAPI.mapstruct.dtos.LoginDto;
import com.example.EnglishAppAPI.mapstruct.dtos.RegisterDto;
import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface IAccountService {
    ResponseEntity<ApiResponse> register(RegisterDto registerDto);
    ResponseEntity<ApiResponse> registerAdminAccount(RegisterDto registerDto);
    ResponseEntity<ApiResponse> login(LoginDto loginDto);
    ResponseEntity<UserEntity> getCurrentUser();
    ResponseEntity<String> resetPasswordWhenLoggedIn(LoginDto loginDto);
    ResponseEntity<String> resetPasswordWhenForget(LoginDto loginDto);
    ResponseEntity<?> sendVerificationEmail(String email);
    ResponseEntity<?> verifyCode(EmailVerificationDto verificationDto);
}
