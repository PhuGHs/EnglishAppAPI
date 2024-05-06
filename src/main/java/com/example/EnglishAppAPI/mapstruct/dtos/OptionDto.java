package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OptionDto {
    @JsonProperty("option_id")
    private Long id;
    @JsonProperty("option_name")
    private String optionName;
    @JsonProperty("is_correct")
    private boolean isCorrect;
    @JsonProperty("question_id")
    private Long questionId;
}
