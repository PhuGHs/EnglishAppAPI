package com.example.EnglishAppAPI.controllers;

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

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterDto registerDto) {
        return accountService.register(registerDto);
    }
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginDto loginDto) {
        return accountService.login(loginDto);
    }

    @GetMapping("/current-user")
    public ResponseEntity<UserEntity> getCurrentUser() {
        return accountService.getCurrentUser();
    }
}
