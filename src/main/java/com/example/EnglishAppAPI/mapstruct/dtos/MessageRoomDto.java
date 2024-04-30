package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageRoomDto {
    @JsonProperty("message_room_id")
    private Long messageRoomId;
    @JsonProperty("room_name")
    private String roomName;
    @JsonProperty("last_message")
    private MessageDto lastSentMessage;
    @JsonProperty("user")
    private UserNecessaryDto lastSentUser;
}
