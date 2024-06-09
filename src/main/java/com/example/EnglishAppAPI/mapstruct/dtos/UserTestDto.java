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
public class UserTestDto {
    @JsonProperty("user_test_id")
    private Long id;
    @JsonProperty("english_test")
    private EnglishTestDto englishTest;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("score")
    private int score;
    @JsonProperty("is_passed")
    private boolean isPassed;
}
