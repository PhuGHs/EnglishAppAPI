package com.example.EnglishAppAPI.mapstruct.mappers;

import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.mapstruct.dtos.UserFindDto;
import com.example.EnglishAppAPI.mapstruct.dtos.UserNecessaryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserNecessaryDto toNecessaryDto(UserEntity user);
    UserFindDto toFindDto(UserEntity user);
}
