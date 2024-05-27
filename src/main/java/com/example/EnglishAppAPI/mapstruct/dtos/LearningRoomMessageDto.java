package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LearningRoomMessageDto {
    @JsonProperty("learning_room_message_id")
    private Long id;
    @JsonProperty("learing_room_id")
    private Long roomId;
    @JsonProperty("user")
    private UserNecessaryDto user;
    @JsonProperty("message")
    private String message;
    @JsonProperty("image")
    private String image;
    @JsonProperty("created_at")
    private Date createdAt;
}
