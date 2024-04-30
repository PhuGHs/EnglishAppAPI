package com.example.EnglishAppAPI.mapstruct.mappers;

import com.example.EnglishAppAPI.entities.Mission;
import com.example.EnglishAppAPI.mapstruct.dtos.MissionPostDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MissionMapper {
    MissionPostDto toDto(Mission mission);
}
