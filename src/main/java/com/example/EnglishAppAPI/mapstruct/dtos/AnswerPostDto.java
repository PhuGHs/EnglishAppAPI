package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AnswerPostDto {
    @JsonProperty("discussion_id")
    private Long discussionId;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("answer_text")
    private String answerText;
}
