package com.example.EnglishAppAPI.mapstruct.mappers;

import com.example.EnglishAppAPI.entities.Review;
import com.example.EnglishAppAPI.mapstruct.dtos.ReviewDto;
import com.example.EnglishAppAPI.mapstruct.dtos.ReviewPostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ReviewMapper {
    @Mapping(source = "userWhoWasReviewed", target = "userWhoWasReviewed")
    @Mapping(source = "userWhoReviewed", target = "userWhoReviewed")
    ReviewDto toDto(Review review);

    @Mapping(source = "userWhoWasReviewedId", target = "userWhoWasReviewed.userId")
    @Mapping(source = "userWhoReviewedId", target = "userWhoReviewed.userId")
    Review toEntity(ReviewPostDto dto);
}
