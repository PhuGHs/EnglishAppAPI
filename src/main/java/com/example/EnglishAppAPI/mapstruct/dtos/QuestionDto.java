package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestionDto {
    @JsonProperty("question_id")
    private Long questionId;
    @JsonProperty("question_name")
    private String questionName;
    @JsonProperty("options")
    private Set<OptionDto> options;
    @JsonProperty("english_test_id")
    private Long englishTestId;
}
