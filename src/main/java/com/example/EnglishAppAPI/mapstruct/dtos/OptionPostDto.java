package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OptionPostDto {
    @JsonProperty("option_name")
    @NotEmpty(message = "option name is required")
    private String optionName;
    @JsonProperty("is_correct")
    private boolean isCorrect;
    @JsonProperty("question_id")
    @NotNull(message = "question id is required")
    private Long questionId;
}
