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
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LearningRoomDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("room_name")
    private String roomName;
    @JsonProperty("created_at")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createdAt;
    @JsonProperty("scheduled_to")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date scheduledTo;
    @JsonProperty("max_participants")
    private int maxParticipants;
    @JsonProperty("duration")
    private int duration;
    @JsonProperty("is_live")
    private boolean isLive;
    @JsonProperty("is_private")
    private boolean isPrivate = false;
    @JsonProperty("topic")
    private EnglishTopicDto topic;
    @JsonProperty("participants")
    private Set<ParticipantDto> participants;
    @JsonProperty("owner")
    private UserNecessaryDto owner;
}
