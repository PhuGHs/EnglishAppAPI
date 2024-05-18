package com.example.EnglishAppAPI.mapstruct.dtos;

import com.example.EnglishAppAPI.entities.EnglishLevel;
import com.example.EnglishAppAPI.entities.Interest;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.*;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserProfileDto {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("gender")
    private boolean gender;
    @JsonProperty("profile_picture")
    private String profilePicture;
    @JsonProperty("following_count")
    private int followingCount;
    @JsonProperty("followers_count")
    private int followersCount;
    @JsonProperty("reviews_count")
    private int reviewsCount;
    @JsonProperty("english_level_name")
    private String englishLevelName;
    @JsonProperty("star")
    private int star;
    @JsonProperty("interests")
    private Set<Interest> interests;
}
