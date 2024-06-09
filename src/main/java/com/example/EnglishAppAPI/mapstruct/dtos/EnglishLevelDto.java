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
public class EnglishLevelDto {
    @JsonProperty("english_level_id")
    private Long levelId;
    @JsonProperty("level_name")
    private String levelName;
    @JsonProperty("description")
    private String description;
}
