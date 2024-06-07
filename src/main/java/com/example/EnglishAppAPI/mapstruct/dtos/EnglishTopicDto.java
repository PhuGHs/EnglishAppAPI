package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnglishTopicDto {
    @JsonProperty("topic_id")
    @NotNull(message = "the topic property is required")
    private Long topicId;
    @JsonProperty("header")
    @NotEmpty(message = "the header must not be empty")
    private String header;
    @JsonProperty("content")
    private String content;
    @JsonProperty("english_level_id")
    @NotNull(message = "the english level is required")
    private Long englishLevelId;
    @JsonProperty("english_level_name")
    private String englishLevelName;
}
