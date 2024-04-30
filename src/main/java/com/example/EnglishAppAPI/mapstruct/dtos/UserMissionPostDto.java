package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserMissionPostDto {
    @JsonProperty("user_id")
    @NotNull(message = "the userId is required")
    private Long userId;
    @JsonProperty("mission_id")
    @NotNull(message = "the missionId is required")
    private Long missionId;
}
