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
public class LeaveRoomDTO {
    @JsonProperty("room_id")
    @NotNull(message = "RoomId field is required")
    private Long roomId;
    @JsonProperty("participant_id")
    @NotNull(message = "ParticipantId field is required")
    private Long participantId;
}
