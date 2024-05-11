package com.example.EnglishAppAPI.mapstruct.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubmitTestDto {
    private int score;
    private Long userId;
}
