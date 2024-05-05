package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JoinLearningRoomDto {
    @JsonProperty("user_id")
    @NotNull(message = "userId is required")
    private Long userId;
    @JsonProperty("room_id")
    @NotNull(message = "roomId is required")
    private Long roomId;
}
