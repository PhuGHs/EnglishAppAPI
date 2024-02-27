package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.dtos.LoginDto;
import com.example.EnglishAppAPI.dtos.RegisterDto;
import com.example.EnglishAppAPI.models.ApiResponse;
import com.example.EnglishAppAPI.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/auth")
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
}
