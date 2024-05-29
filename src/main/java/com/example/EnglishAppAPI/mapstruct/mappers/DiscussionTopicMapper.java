package com.example.EnglishAppAPI.mapstruct.mappers;

import com.example.EnglishAppAPI.entities.DiscussionTopic;
import com.example.EnglishAppAPI.mapstruct.dtos.DiscussionTopicDto;
import com.example.EnglishAppAPI.mapstruct.dtos.DiscussionTopicPostDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiscussionTopicMapper {
    DiscussionTopic toEntity(DiscussionTopicPostDto discussionTopicPostDto);
    DiscussionTopicDto toDto(DiscussionTopic discussionTopic);
}
