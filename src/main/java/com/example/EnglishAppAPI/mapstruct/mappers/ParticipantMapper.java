package com.example.EnglishAppAPI.mapstruct.mappers;

import com.example.EnglishAppAPI.entities.Participant;
import com.example.EnglishAppAPI.mapstruct.dtos.ParticipantDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ParticipantMapper {
    @Mapping(source = "user", target = "user")
    ParticipantDto toDto(Participant participant);
}
