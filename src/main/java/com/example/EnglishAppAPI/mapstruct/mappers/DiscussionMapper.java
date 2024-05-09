package com.example.EnglishAppAPI.mapstruct.mappers;

import com.example.EnglishAppAPI.entities.Discussion;
import com.example.EnglishAppAPI.entities.EnglishTopic;
import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.entities.indexes.DiscussionDocument;
import com.example.EnglishAppAPI.mapstruct.dtos.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {UserMapper.class, EnglishTopicMapper.class})
public interface DiscussionMapper {
    @Mapping(source = "user", target = "user")
    @Mapping(source = "topic", target = "topic")
    DiscussionDto toDto(Discussion discussion);

    @Mapping(source = "user", target = "user")
    @Mapping(source = "topic", target = "topic")
    DiscussionDocument toDocument(Discussion discussion);
}
