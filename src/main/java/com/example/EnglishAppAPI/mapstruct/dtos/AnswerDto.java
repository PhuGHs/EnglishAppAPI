package com.example.EnglishAppAPI.mapstruct.dtos;

import com.example.EnglishAppAPI.mapstruct.serializers.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AnswerDto {
    @JsonProperty("answer_id")
    private Long answerId;
    @JsonProperty("created_date")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createdDate;
    @JsonProperty("updated_date")
    private Date updatedDate;
    @JsonProperty("discussion_id")
    private Long discussionId;
    @JsonProperty("user")
    private UserNecessaryDto user;
    @JsonProperty("answer_text")
    private String answerText;
}
