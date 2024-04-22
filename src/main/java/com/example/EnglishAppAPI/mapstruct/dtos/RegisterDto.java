package com.example.EnglishAppAPI.mapstruct.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
public class RegisterDto implements Serializable {
    @Email(message = "Provided email is invalid")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @Length(min = 6, max = 12, message = "Password length must be between 6 and 12 characters")
    private String password;
    @Length(min = 6, max = 12, message = "Password length must be between 6 and 12 characters")
    private String confirmedPassword;
    @NotBlank(message = "fullName is a require attribute")
    private String fullName;
    private Boolean isMale;

    public RegisterDto(String email, String password, String fullName, Boolean isMale) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.isMale = isMale;
    }
}
