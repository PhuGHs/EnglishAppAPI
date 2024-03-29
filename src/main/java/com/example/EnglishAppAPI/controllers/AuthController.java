package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.dtos.LoginDto;
import com.example.EnglishAppAPI.dtos.RegisterDto;
import com.example.EnglishAppAPI.models.ApiResponse;
import com.example.EnglishAppAPI.services.IAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RestControllerAdvice
@RequestMapping(path = "/api/v1/auth")
@Validated
public class AuthController {
    @Autowired
    private IAccountService accountService;

    public AuthController(IAccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterDto registerDto) {
        return accountService.register(registerDto);
    }
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginDto loginDto) {
        return accountService.login(loginDto);
    }
}
