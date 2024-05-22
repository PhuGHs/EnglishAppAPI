package com.example.EnglishAppAPI.mapstruct.dtos;

import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginDto {
    @Email(message = "Provided email is invalid")
    private String email;
    @Length(min = 6, max = 24, message = "Password length must be between 6 and 24 characters")
    private String password;
}
