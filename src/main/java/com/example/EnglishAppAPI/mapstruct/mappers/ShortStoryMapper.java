package com.example.EnglishAppAPI.mapstruct.mappers;

import com.example.EnglishAppAPI.entities.ShortStory;
import com.example.EnglishAppAPI.mapstruct.dtos.ShortStoryDto;
import com.example.EnglishAppAPI.mapstruct.dtos.ShortStoryPostDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShortStoryMapper {
    ShortStoryDto toDto(ShortStory shortStory);
    ShortStory toEntity(ShortStoryPostDto shortStoryPostDto);
}
