package com.example.EnglishAppAPI.entities.indexes;

import com.example.EnglishAppAPI.entities.Discussion;
import com.example.EnglishAppAPI.entities.EnglishTopic;
import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTopicDto;
import com.example.EnglishAppAPI.mapstruct.dtos.UserElasDto;
import com.example.EnglishAppAPI.mapstruct.dtos.UserNecessaryDto;
import com.example.EnglishAppAPI.mapstruct.serializers.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.Date;

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
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createdDate;
    @Field(name = "updated_date")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date updatedDate;
    @Field(name = "user", type = FieldType.Object)
    private UserElasDto user;
    @Field(type = FieldType.Object, name = "topic")
    private EnglishTopicDto topic;

    public static DiscussionDocument fromEntity(Discussion discussion, UserElasDto userNecessaryDto, EnglishTopicDto englishTopicDto) {
        DiscussionDocument discussionDocument = new DiscussionDocument();
        discussionDocument.id = discussion.getId();
        discussionDocument.title = discussion.getTitle();
        discussionDocument.numberOfAnswers = discussion.getNumberOfAnswers();
        discussionDocument.setUser(userNecessaryDto);
        discussionDocument.createdDate = discussion.getCreatedDate();
        discussionDocument.updatedDate = discussion.getUpdatedDate();
        discussionDocument.setTopic(englishTopicDto);
        return discussionDocument;
    }
}
