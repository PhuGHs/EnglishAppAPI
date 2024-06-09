package com.example.EnglishAppAPI.mapstruct.mappers;

import com.example.EnglishAppAPI.entities.EnglishLevel;
import com.example.EnglishAppAPI.mapstruct.dtos.EnglishLevelDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnglishLevelMapper {
    @Mapping(source = "levelId", target = "levelId")
    @Mapping(source = "levelName", target = "levelName")
    @Mapping(source = "description", target = "description")
    EnglishLevelDto toDto(EnglishLevel englishLevel);
}
