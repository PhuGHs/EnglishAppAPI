package com.example.EnglishAppAPI.mapstruct.mappers;

import com.example.EnglishAppAPI.entities.Message;
import com.example.EnglishAppAPI.mapstruct.dtos.MessageDto;
import com.example.EnglishAppAPI.mapstruct.dtos.MessagePostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class}, imports = { java.time.LocalDateTime.class })
public interface MessageMapper {
    @Mapping(source = "messageRoom.messageRoomId", target = "messageRoomId")
    @Mapping(source = "sender", target = "sender")
    @Mapping(source = "receiver", target = "receiver")
    @Mapping(source = "createdAt", target = "createdAt")
    MessageDto toDto(Message message);

    @Mapping(source = "messageRoomId", target = "messageRoom.messageRoomId")
    @Mapping(source = "senderId", target = "sender.userId")
    @Mapping(source = "receiverId", target = "receiver.userId")
    Message toEntity(MessagePostDto messagePostDto);
}
