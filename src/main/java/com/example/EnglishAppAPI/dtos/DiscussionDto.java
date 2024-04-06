package com.example.EnglishAppAPI.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class DiscussionDto {
    private Long id;
    private String title;
    private Long userId;
    private Long englishTopicId;
}
