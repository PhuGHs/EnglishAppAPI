package com.example.EnglishAppAPI.mapstruct.dtos;

import com.example.EnglishAppAPI.mapstruct.serializers.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

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
    @JsonProperty("topic")
    private DiscussionTopicDto topic;
    @JsonProperty("number_of_answers")
    private int numberOfAnswers;
    @JsonProperty("created_date")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createdDate;
    @JsonProperty("updated_date")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date updatedDate;
}
