package com.example.EnglishAppAPI.dtos;

import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class LoginDto {
    @Email(message = "Provided email is invalid")
    private String email;
    @Length(min = 6, max = 12, message = "Password length must be between 6 and 12 characters")
    private String password;
}
