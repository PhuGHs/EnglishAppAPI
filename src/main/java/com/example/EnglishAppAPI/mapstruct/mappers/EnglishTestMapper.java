package com.example.EnglishAppAPI.mapstruct.mappers;

import com.example.EnglishAppAPI.entities.EnglishTest;
import com.example.EnglishAppAPI.entities.Question;
import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTestDto;
import com.example.EnglishAppAPI.mapstruct.dtos.QuestionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnglishTestMapper {
    EnglishTestDto toEnglishTestDto(EnglishTest englishTest);
}
