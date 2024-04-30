package com.example.EnglishAppAPI.mapstruct.mappers;

import com.example.EnglishAppAPI.entities.UserMission;
import com.example.EnglishAppAPI.mapstruct.dtos.UserMissionGetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MissionMapper.class})
public interface UserMissionMapper {
    @Mapping(source = "mission", target = "mission")
    @Mapping(source = "user.userId", target = "userId")
    UserMissionGetDto toDto(UserMission mission);
}
