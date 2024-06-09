package com.example.EnglishAppAPI.mapstruct.mappers;

import com.example.EnglishAppAPI.entities.EnglishTest;
import com.example.EnglishAppAPI.entities.Question;
import com.example.EnglishAppAPI.entities.UserTest;
import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTestDto;
import com.example.EnglishAppAPI.mapstruct.dtos.QuestionDto;
import com.example.EnglishAppAPI.mapstruct.dtos.UserTestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnglishTestMapper {
    @Mapping(source = "englishLevel.levelId", target = "englishLevelId")
    EnglishTestDto toEnglishTestDto(EnglishTest englishTest);

    @Mapping(source = "test", target = "englishTest")
    @Mapping(source = "user.userId", target = "userId")
    UserTestDto toUserTestDto(UserTest userTest);
}
