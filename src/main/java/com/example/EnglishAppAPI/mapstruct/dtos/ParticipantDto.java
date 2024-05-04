package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ParticipantDto {
    @JsonProperty("participant_id")
    private Long id;
    @JsonProperty("user")
    private UserNecessaryDto user;
    @JsonProperty("join_time")
    private LocalDateTime joinTime;
    @JsonProperty("is_speaker")
    private boolean isSpeaker;
    @JsonProperty("is_owner")
    private boolean isOwner;
}
