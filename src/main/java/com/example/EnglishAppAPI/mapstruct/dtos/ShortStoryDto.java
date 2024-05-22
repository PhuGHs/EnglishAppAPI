package com.example.EnglishAppAPI.mapstruct.dtos;

import com.example.EnglishAppAPI.mapstruct.serializers.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

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
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createdDate;
}
