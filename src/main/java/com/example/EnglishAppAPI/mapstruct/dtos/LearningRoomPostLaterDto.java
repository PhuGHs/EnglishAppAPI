package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LearningRoomPostLaterDto {
    @JsonProperty("room_name")
    @NotEmpty(message = "room name is required")
    private String roomName;
    @JsonProperty("created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    @JsonProperty("scheduled_to")
    private LocalDateTime scheduledTo;
    @JsonProperty("max_participants")
    @NotNull(message = "max participants is required")
    private int maxParticipants;
    @JsonProperty("duration")
    @Range(min = 1, max = 4, message = "the duration must be between 1 to 4 hours")
    private int duration;
    @JsonProperty("topic_id")
    @NotNull(message = "english topic is required")
    private Long englishTopicId;
    @JsonProperty("is_private")
    private boolean isPrivate;
    @JsonProperty("password")
    private String password;
    @JsonProperty("user_id")
    @NotNull(message = "userId is required")
    private Long userId;
}
