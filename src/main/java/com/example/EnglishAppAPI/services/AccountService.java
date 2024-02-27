package com.example.EnglishAppAPI.services;

import com.example.EnglishAppAPI.dtos.LoginDto;
import com.example.EnglishAppAPI.dtos.RegisterDto;
import com.example.EnglishAppAPI.models.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface AccountService {
    ResponseEntity<ApiResponse> register(RegisterDto registerDto);
    ResponseEntity<ApiResponse> login(LoginDto loginDto);
}
