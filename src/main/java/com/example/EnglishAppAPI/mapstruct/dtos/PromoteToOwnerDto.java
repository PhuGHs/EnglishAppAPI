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
public class PromoteToOwnerDto {
    @JsonProperty("participant_id")
    @NotNull(message = "participantId is required")
    private Long participantId;
    @JsonProperty("owner_id")
    @NotNull(message = "ownerId is required")
    private Long ownerId;
}
