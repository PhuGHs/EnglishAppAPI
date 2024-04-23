package com.example.EnglishAppAPI.mapstruct.mappers;

import com.example.EnglishAppAPI.entities.Answer;
import com.example.EnglishAppAPI.entities.EnglishTopic;
import com.example.EnglishAppAPI.mapstruct.dtos.AnswerDto;
import com.example.EnglishAppAPI.mapstruct.dtos.AnswerGetDto;
import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTopicDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.annotation.processing.Generated;

@Mapper(componentModel = "spring")
public interface MapStructMapper {
    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "discussionId", source = "discussion.id")
    AnswerGetDto answerToDto(Answer answer);

    Answer answerDtoToEntity(AnswerDto answerDto);
}

