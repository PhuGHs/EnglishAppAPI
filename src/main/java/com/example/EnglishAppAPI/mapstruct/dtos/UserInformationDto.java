package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInformationDto {
    @JsonProperty("profile_picture")
    private String profilePicture;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("gender")
    private boolean gender;
    @JsonProperty("quote")
    private String quote;
}
