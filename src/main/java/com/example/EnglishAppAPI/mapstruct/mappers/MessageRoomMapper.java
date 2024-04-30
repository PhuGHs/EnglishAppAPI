package com.example.EnglishAppAPI.mapstruct.mappers;

import com.example.EnglishAppAPI.entities.MessageRoom;
import com.example.EnglishAppAPI.mapstruct.dtos.MessageRoomDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, MessageMapper.class})
public interface MessageRoomMapper {
    @Mapping(source = "lastSentMessage", target = "lastSentMessage")
    @Mapping(source = "lastSentUser", target = "lastSentUser")
    MessageRoomDto toDto(MessageRoom messageRoom);
}
