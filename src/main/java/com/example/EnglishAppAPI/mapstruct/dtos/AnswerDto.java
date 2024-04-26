package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AnswerDto {
    @JsonProperty("answer_id")
    private Long answerId;
    @JsonProperty("created_date")
    private LocalDateTime createdDate;
    @JsonProperty("updated_date")
    private LocalDateTime updatedDate;
    @JsonProperty("discussion_id")
    private Long discussionId;
    @JsonProperty("user")
    private UserNecessaryDto user;
    @JsonProperty("answer_text")
    private String answerText;
}
