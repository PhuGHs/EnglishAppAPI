package com.example.EnglishAppAPI.mapstruct.dtos;

import com.example.EnglishAppAPI.entities.EnglishLevel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserNecessaryDto {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("profile_picture")
    private String profilePicture;
    @JsonProperty("english_level")
    private EnglishLevel englishLevel;
}
