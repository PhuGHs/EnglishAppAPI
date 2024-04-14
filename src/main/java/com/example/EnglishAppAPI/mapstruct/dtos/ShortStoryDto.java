package com.example.EnglishAppAPI.mapstruct.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ShortStoryDto {
    private String title;
    private String paragraph;
    private String image;
}
