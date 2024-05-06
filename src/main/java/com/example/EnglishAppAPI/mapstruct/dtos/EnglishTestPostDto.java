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
@Setter
@Getter
public class EnglishTestPostDto {
    @JsonProperty("number_of_questions")
    @NotNull(message = "number of questions is required")
    private int numberOfQuestions;
    @JsonProperty("title")
    @NotEmpty(message = "title is required")
    private String title;
    @JsonProperty("description")
    @NotEmpty(message = "description")
    private String description;
}
