package com.example.EnglishAppAPI.mapstruct.dtos;

import com.example.EnglishAppAPI.entities.Participant;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class WebsocketType<T> {
    @JsonProperty("type")
    private String type;
    @JsonProperty("data")
    private T data;
}
