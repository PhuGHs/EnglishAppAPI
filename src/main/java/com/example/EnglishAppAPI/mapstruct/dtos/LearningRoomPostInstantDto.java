package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LearningRoomPostInstantDto {
    @JsonProperty("room_name")
    @NotEmpty(message = "room name is required")
    private String roomName;
    @JsonProperty("created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    @JsonProperty("max_participants")
    @NotNull(message = "max participants is required")
    private int maxParticipants;
    @JsonProperty("topic_id")
    @NotNull(message = "english topic is required")
    private Long englishTopicId;
    @JsonProperty("user_id")
    @NotNull(message = "userId is required")
    private Long userId;
}
