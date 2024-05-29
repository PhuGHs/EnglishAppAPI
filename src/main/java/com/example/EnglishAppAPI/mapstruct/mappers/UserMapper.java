package com.example.EnglishAppAPI.mapstruct.mappers;

import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.mapstruct.dtos.UserElasDto;
import com.example.EnglishAppAPI.mapstruct.dtos.UserFindDto;
import com.example.EnglishAppAPI.mapstruct.dtos.UserNecessaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserNecessaryDto toNecessaryDto(UserEntity user);
    @Mapping(source = "englishLevel.levelName", target = "englishLevelName")
    UserElasDto toElas(UserEntity user);
    UserFindDto toFindDto(UserEntity user);
}
