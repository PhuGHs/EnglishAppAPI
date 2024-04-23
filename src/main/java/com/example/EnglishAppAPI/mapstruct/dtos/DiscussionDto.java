package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscussionDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("user")
    private UserNecessaryDto user;
    @JsonProperty("english_topic")
    private EnglishTopicDto topic;
    @JsonProperty("number_of_answers")
    private int numberOfAnswers;
    @JsonProperty("created_date")
    private LocalDateTime createdDate;
    @JsonProperty("updated_date")
    private LocalDateTime updatedDate;
}
