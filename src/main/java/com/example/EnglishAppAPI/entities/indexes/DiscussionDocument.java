package com.example.EnglishAppAPI.entities.indexes;

import com.example.EnglishAppAPI.entities.EnglishTopic;
import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTopicDto;
import com.example.EnglishAppAPI.mapstruct.dtos.UserNecessaryDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "discussions_index", createIndex = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscussionDocument {
    private Long id;
    @Field(type = FieldType.Text, name = "title")
    private String title;
    @Field(type = FieldType.Integer, name = "number_of_answers")
    private int numberOfAnswers;
    @Field(name = "created_date")
    private LocalDateTime createdDate;
    @Field(name = "updated_date")
    private LocalDateTime updatedDate;
    @Field(name = "user", type = FieldType.Object)
    private UserNecessaryDto user;
    @Field(type = FieldType.Object, name = "topic")
    private EnglishTopicDto topic;
}
