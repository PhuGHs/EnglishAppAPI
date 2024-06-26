package com.example.EnglishAppAPI.mapstruct.dtos;

import com.example.EnglishAppAPI.entities.EnglishLevel;
import com.example.EnglishAppAPI.entities.Interest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserFindDto {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("profile_picture")
    private String profilePicture;
    @JsonProperty("english_level")
    private EnglishLevel englishLevel;
    @JsonProperty("interests")
    private Set<Interest> interests;
}
