package com.example.EnglishAppAPI.mapstruct.dtos;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDto {
    @Email(message = "Provided email is invalid")
    private String email;
    @Length(min = 6, max = 24, message = "Password length must be between 6 and 24 characters")
    private String password;
    @Length(min = 6, max = 24, message = "Password length must be between 6 and 24 characters")
    private String newPassword;
}
