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
public class LearningRoomMessagePostDto {
    @JsonProperty("learning_room_id")
    private Long roomId;
    @JsonProperty("message")
    private String message;
    @JsonProperty("image")
    private String image;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("user_id")
    private Long userId;
}
