package com.example.EnglishAppAPI.entities.indexes;

import com.example.EnglishAppAPI.entities.Discussion;
import com.example.EnglishAppAPI.mapstruct.dtos.DiscussionTopicDto;
import com.example.EnglishAppAPI.mapstruct.dtos.UserElasDto;
import com.example.EnglishAppAPI.mapstruct.serializers.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "discussion_index", createIndex = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscussionDocument {
    private Long id;
    @Field(type = FieldType.Text, name = "title")
    private String title;
    @Field(type = FieldType.Integer, name = "number_of_answers")
    private int number_of_answers;
    @Field(name = "created_date", type = FieldType.Date)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date created_date;
    @Field(name = "updated_date", type = FieldType.Date)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date updated_date;
    @Field(name = "user", type = FieldType.Object)
    private UserElasDto user;
    @Field(type = FieldType.Object, name = "topic")
    private DiscussionTopicDto topic;

    public static DiscussionDocument fromEntity(Discussion discussion, UserElasDto userNecessaryDto, DiscussionTopicDto englishTopicDto) {
        DiscussionDocument discussionDocument = new DiscussionDocument();
        discussionDocument.id = discussion.getId();
        discussionDocument.title = discussion.getTitle();
        discussionDocument.number_of_answers = discussion.getNumberOfAnswers();
        discussionDocument.setUser(userNecessaryDto);
        discussionDocument.created_date = discussion.getCreatedDate();
        discussionDocument.updated_date = discussion.getUpdatedDate();
        discussionDocument.setTopic(englishTopicDto);
        return discussionDocument;
    }
}
