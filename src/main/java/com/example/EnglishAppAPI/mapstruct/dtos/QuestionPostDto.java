package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestionPostDto {
    @JsonProperty("question_name")
    @NotEmpty(message = "question_name is required")
    private String questionName;
    @JsonProperty("english_test_id")
    private Long englishTestId;
    @JsonProperty("options")
    private Set<OptionPostDto> options;
}
