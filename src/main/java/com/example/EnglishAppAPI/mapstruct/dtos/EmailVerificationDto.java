package com.example.EnglishAppAPI.mapstruct.dtos;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailVerificationDto {
    @Email(message = "Provided email is invalid")
    private String email;
    @Length(min = 6, max = 6, message = "The email verification code must have 6 digits")
    private String code;
}
