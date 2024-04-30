package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MissionPostDto {
    @JsonProperty("mission_id")
    private Long missionId;
    @JsonProperty("mission_name")
    private String missionName;
    @JsonProperty("points_awarded")
    private int pointsAwarded;
    @JsonProperty("max_completion_count")
    private int maxCompletionCount;
}
