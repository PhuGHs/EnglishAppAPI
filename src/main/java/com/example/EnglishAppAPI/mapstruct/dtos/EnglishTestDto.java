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
public class EnglishTestDto {
    @JsonProperty("english_test_id")
    private Long englishTestId;
    @JsonProperty("number_of_questions")
    private int numberOfQuestions;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
}
