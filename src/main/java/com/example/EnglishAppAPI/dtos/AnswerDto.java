package com.example.EnglishAppAPI.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class AnswerDto {
    private Long discussionId;
    private Long userId;
    private String answerText;
}
