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
public class JoinLearningRoom {
    @JsonProperty("room_id")
    private Long roomId;
    @JsonProperty("password")
    private String password;
    @JsonProperty("user_id")
    private Long userId;
}
