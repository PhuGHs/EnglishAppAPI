package com.example.EnglishAppAPI.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
public class RegisterDto implements Serializable {
    @Email(message = "Provided email is invalid")
    private String email;
    @Length(min = 6, max = 12, message = "Password length must be between 6 and 12 characters")
    private String password;
    @Length(min = 6, max = 12, message = "Password length must be between 6 and 12 characters")
    private String confirmedPassword;

    public RegisterDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
