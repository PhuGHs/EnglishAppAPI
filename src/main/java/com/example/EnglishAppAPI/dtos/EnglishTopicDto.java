package com.example.EnglishAppAPI.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class EnglishTopicDto {
    private String header;
    private String content;
    private Long englishLevelId;
}
