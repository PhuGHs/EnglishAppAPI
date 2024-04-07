package com.example.EnglishAppAPI.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class EnglishTopicQuestionDto {
    private String question;
    private String sampleAnswers;
}
