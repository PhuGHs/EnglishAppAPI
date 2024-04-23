package com.example.EnglishAppAPI.mapstruct.mappers;

import com.example.EnglishAppAPI.entities.Discussion;
import com.example.EnglishAppAPI.entities.EnglishTopic;
import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.mapstruct.dtos.DiscussionDto;
import com.example.EnglishAppAPI.mapstruct.dtos.DiscussionPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTopicDto;
import com.example.EnglishAppAPI.mapstruct.dtos.UserNecessaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public interface DiscussionMapper {
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    EnglishTopicMapper englishTopicMapper = Mappers.getMapper(EnglishTopicMapper.class);

    @Mapping(source = "user", target = "user", qualifiedByName = "mapUserEntityToUserNecessaryDto")
    @Mapping(source = "topic", target = "topic", qualifiedByName = "mapEnglishTopicToEnglishTopicDto")
    DiscussionDto toDto(Discussion discussion);

//    Discussion toEntity(DiscussionPostDto discussionPostDto);

    default UserNecessaryDto mapUserEntityToUserNecessaryDto(UserEntity user) {
        return userMapper.toNecessaryDto(user);
    }

    default EnglishTopicDto mapEnglishTopicToEnglishTopicDto(EnglishTopic topic) {
        return englishTopicMapper.toDto(topic);
    }

    default EnglishTopic mapEnglishTopicDtoToEnglishTopic(EnglishTopicDto englishTopicDto) {
        return englishTopicMapper.toEntity(englishTopicDto);
    }
}
