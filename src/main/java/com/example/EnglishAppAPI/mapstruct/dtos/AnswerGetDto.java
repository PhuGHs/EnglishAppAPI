package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class AnswerGetDto {
    @JsonProperty("answer_id")
    private Long answerId;
    @JsonProperty("discussion_id")
    private Long discussionId;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("answer_text")
    private String answerText;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
