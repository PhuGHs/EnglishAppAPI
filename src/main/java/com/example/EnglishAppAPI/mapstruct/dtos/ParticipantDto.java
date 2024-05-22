package com.example.EnglishAppAPI.mapstruct.dtos;

import com.example.EnglishAppAPI.mapstruct.serializers.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

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
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date joinTime;
    @JsonProperty("is_speaker")
    private boolean isSpeaker;
    @JsonProperty("is_owner")
    private boolean isOwner;
}
