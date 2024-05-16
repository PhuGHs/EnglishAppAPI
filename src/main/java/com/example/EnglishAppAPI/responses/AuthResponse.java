package com.example.EnglishAppAPI.responses;

import com.example.EnglishAppAPI.entities.Account;
import com.example.EnglishAppAPI.mapstruct.dtos.AccountDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String accessToken;
    private AccountDto account;
}
