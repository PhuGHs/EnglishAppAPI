package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnglishTopicPostDto {
    @JsonProperty("header")
    @NotEmpty(message = "The header property is required")
    private String header;
    @JsonProperty("content")
    @NotEmpty(message = "The content property is required")
    private String content;
    @JsonProperty("english_level_id")
    private Long englishLevelId;
}
