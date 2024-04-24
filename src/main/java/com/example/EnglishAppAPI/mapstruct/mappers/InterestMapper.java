package com.example.EnglishAppAPI.mapstruct.mappers;

import com.example.EnglishAppAPI.entities.Interest;
import com.example.EnglishAppAPI.mapstruct.dtos.InterestDto;
import com.example.EnglishAppAPI.mapstruct.dtos.InterestPostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InterestMapper {
    @Mapping(source = "name", target = "interestName")
    Interest toEntity(InterestPostDto interestPostDto);
    @Mapping(source = "interestName", target="name")
    @Mapping(source = "interestId", target = "id")
    InterestDto toDto(Interest interest);
}
