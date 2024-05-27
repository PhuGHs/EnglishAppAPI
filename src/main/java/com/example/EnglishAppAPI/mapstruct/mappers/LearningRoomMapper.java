package com.example.EnglishAppAPI.mapstruct.mappers;

import com.example.EnglishAppAPI.entities.LearningRoom;
import com.example.EnglishAppAPI.entities.LearningRoomMessage;
import com.example.EnglishAppAPI.entities.Participant;
import com.example.EnglishAppAPI.mapstruct.dtos.LearningRoomDto;
import com.example.EnglishAppAPI.mapstruct.dtos.LearningRoomMessageDto;
import com.example.EnglishAppAPI.mapstruct.dtos.LearningRoomMessagePostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.ParticipantDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ParticipantMapper.class, EnglishTopicMapper.class, UserMapper.class})
public interface LearningRoomMapper {
    @Mapping(source = "participants", target = "participants")
    @Mapping(source = "topic", target = "topic")
    LearningRoomDto toDto(LearningRoom learningRoom);

    @Mapping(source = "roomId", target = "learningRoom.id")
    @Mapping(source = "userId", target="user.userId")
    LearningRoomMessage toMessageEntity(LearningRoomMessagePostDto learningRoomMessagePostDto);

    @Mapping(source = "learningRoom.id", target = "roomId")
    @Mapping(source = "user", target = "user")
    LearningRoomMessageDto toMessageDto(LearningRoomMessage learningRoomMessage);
}
