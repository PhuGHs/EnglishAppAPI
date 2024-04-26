package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShortStoryDto {
    @JsonProperty("short_story_id")
    private Long id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("paragraph")
    private String paragraph;
    @JsonProperty("number_of_likes")
    private int numberOfLikes;
    @JsonProperty("image")
    private String image;
    @JsonProperty("created_date")
    private LocalDateTime createdDate;
}
