package com.example.EnglishAppAPI.mapstruct.mappers;

import com.example.EnglishAppAPI.entities.LearningRoom;
import com.example.EnglishAppAPI.entities.Participant;
import com.example.EnglishAppAPI.mapstruct.dtos.LearningRoomDto;
import com.example.EnglishAppAPI.mapstruct.dtos.ParticipantDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ParticipantMapper.class, EnglishTopicMapper.class})
public interface LearningRoomMapper {
    @Mapping(source = "participants", target = "participants")
    @Mapping(source = "topic", target = "topic")
    LearningRoomDto toDto(LearningRoom learningRoom);
}
