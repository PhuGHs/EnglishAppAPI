package com.example.EnglishAppAPI.mapstruct.mappers;

import com.example.EnglishAppAPI.entities.Answer;
import com.example.EnglishAppAPI.mapstruct.dtos.AnswerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface AnswerMapper {
    @Mapping(source = "discussion.id", target = "discussionId")
    @Mapping(source = "createdAt", target = "createdDate")
    @Mapping(source = "updatedAt", target = "updatedDate")
    @Mapping(source = "user", target = "user")
    AnswerDto toDto(Answer answer);
}
