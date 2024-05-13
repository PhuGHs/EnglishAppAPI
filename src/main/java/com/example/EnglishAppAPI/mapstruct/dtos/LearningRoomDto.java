package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
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
    private LocalDateTime createdAt;
    @JsonProperty("scheduled_to")
    private LocalDateTime scheduledTo;
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
}
