package com.example.EnglishAppAPI.mapstruct.mappers;

import com.example.EnglishAppAPI.entities.EnglishTopicQuestion;
import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTopicPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTopicQuestionDto;
import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTopicQuestionPostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface EnglishTopicQuestionMapper {
    @Mapping(source = "questionName", target = "question")
    @Mapping(source = "topic.topicId", target = "id")
    EnglishTopicQuestionDto toDtoQ(EnglishTopicQuestion englishTopicQuestion);

    @Mapping(source = "question", target = "questionName")
    @Mapping(source = "topicId", target = "topic.topicId")
    EnglishTopicQuestion toEntityQ(EnglishTopicQuestionPostDto englishTopicPostDto);
}
