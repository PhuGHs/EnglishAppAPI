package com.example.EnglishAppAPI.mapstruct.dtos;

import com.example.EnglishAppAPI.entities.EnglishLevel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Field;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserElasDto {
    @JsonProperty("user_id")
    @Field(name = "user_id")
    private Long userId;
    @JsonProperty("full_name")
    @Field(name = "full_name")
    private String fullName;
    @JsonProperty("profile_picture")
    @Field(name = "profile_picture")
    private String profilePicture;
    @JsonProperty("english_level_name")
    @Field(name = "english_level_name")
    private String englishLevelName;
}
