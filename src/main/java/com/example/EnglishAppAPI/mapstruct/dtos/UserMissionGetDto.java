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
public class UserMissionGetDto {
    @JsonProperty("user_mission_id")
    private Long id;
    @JsonProperty("mission")
    private MissionPostDto mission;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("is_completed")
    private boolean isCompleted;
    @JsonProperty("completion_count")
    private int completionCount;
}
