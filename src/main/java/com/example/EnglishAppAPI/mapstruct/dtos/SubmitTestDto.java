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
public class SubmitTestDto {
    @JsonProperty("english_test_id")
    private Long id;
    @JsonProperty("score")
    private int score;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("is_entry_level_test")
    private boolean isEntryLevelTest;
}
