package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ShortStoryDto {
    @JsonProperty("title")
    @NotEmpty(message = "this title field is required")
    private String title;
    @JsonProperty("paragraph")
    @NotEmpty(message = "this paragraph field is required")
    private String paragraph;
    @JsonProperty("image")
    @NotEmpty(message = "this image field is required")
    private String image;
}
