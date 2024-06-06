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
@Getter
@Setter
public class MessagePostDto {
    @JsonProperty("message_room_id")
    @NotNull(message = "the message roomId is required")
    private Long messageRoomId;
    @JsonProperty("sender_id")
    @NotNull(message = "the sender id property is required")
    private Long senderId;
    @JsonProperty("receiver_id")
    @NotNull(message = "the receiver id property is required")
    private Long receiverId;
    @JsonProperty("message")
    @NotEmpty(message = "the message must have value")
    private String message;
    @JsonProperty("is_read")
    private boolean isRead = false;
    @JsonProperty("image")
    private String image = "";
    @JsonProperty("invitation")
    private String invitation;

}
