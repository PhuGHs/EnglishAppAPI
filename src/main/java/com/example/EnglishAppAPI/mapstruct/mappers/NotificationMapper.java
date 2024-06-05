package com.example.EnglishAppAPI.mapstruct.mappers;

import com.example.EnglishAppAPI.entities.Notification;
import com.example.EnglishAppAPI.mapstruct.dtos.NotificationDto;
import com.example.EnglishAppAPI.mapstruct.dtos.NotificationPostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface NotificationMapper {
    @Mapping(source = "sender", target = "sender")
    @Mapping(source = "receiver", target = "receiver")
    @Mapping(source = "type", target = "type")
    NotificationDto toDto(Notification notification);

    @Mapping(source = "senderId", target = "sender.userId")
    @Mapping(source = "receiverId", target = "receiver.userId")
    @Mapping(source = "type", target = "type")
    Notification toEntity(NotificationPostDto notificationPostDto);
}
