package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class DiscussionPostDto {
    @JsonProperty("title")
    @NotEmpty(message = "the title is required")
    private String title;
    @JsonProperty("user_id")
    @NotNull(message = "user is required")
    private Long userId;
    @JsonProperty("english_topic_id")
    @NotNull(message = "topic is required")
    private Long englishTopicId;
}
