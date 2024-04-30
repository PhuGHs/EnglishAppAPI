package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageDto {
    @JsonProperty("message_id")
    private Long messageId;
    @JsonProperty("message_room_id")
    private Long messageRoomId;
    @JsonProperty("sender")
    private UserNecessaryDto sender;
    @JsonProperty("receiver")
    private UserNecessaryDto receiver;
    @JsonProperty("message")
    private String message;
    @JsonProperty("is_read")
    private boolean isRead;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("image")
    private String image;
}
