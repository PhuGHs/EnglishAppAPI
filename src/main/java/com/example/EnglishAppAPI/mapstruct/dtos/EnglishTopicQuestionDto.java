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
public class EnglishTopicQuestionDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("question")
    private String question;
    @JsonProperty("sample_answer")
    private String sampleAnswer;
}
