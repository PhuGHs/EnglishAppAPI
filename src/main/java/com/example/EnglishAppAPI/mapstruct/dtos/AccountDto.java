package com.example.EnglishAppAPI.mapstruct.dtos;

import com.example.EnglishAppAPI.entities.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountDto {
    @JsonProperty("account_id")
    private Long accountId;
    @JsonProperty("user")
    private UserNecessaryDto user;
    @JsonProperty("role")
    private Role role;
    @JsonProperty("email")
    private String email;
    @JsonProperty("is_active")
    private boolean isActive;
    @JsonProperty("is_banned")
    private boolean isBanned;
}
