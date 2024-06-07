package com.example.EnglishAppAPI.mapstruct.mappers;

import com.example.EnglishAppAPI.entities.EnglishTopic;
import com.example.EnglishAppAPI.entities.EnglishTopicQuestion;
import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTopicDto;
import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTopicPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTopicQuestionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnglishTopicMapper {
    @Mapping(source = "englishLevel.levelId", target = "englishLevelId")
    @Mapping(source = "englishLevel.levelName", target = "englishLevelName")
    EnglishTopicDto toDto(EnglishTopic englishTopic);
    @Mapping(target = "englishLevel.levelId", source = "englishLevelId")
    EnglishTopic toEntity(EnglishTopicDto englishTopicDto);
}
