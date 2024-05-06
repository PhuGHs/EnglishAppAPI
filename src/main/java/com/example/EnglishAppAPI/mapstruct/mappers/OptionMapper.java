package com.example.EnglishAppAPI.mapstruct.mappers;

import com.example.EnglishAppAPI.entities.Option;
import com.example.EnglishAppAPI.mapstruct.dtos.OptionDto;
import com.example.EnglishAppAPI.mapstruct.dtos.OptionPostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OptionMapper {
    @Mapping(source = "questionId", target = "question.questionId")
    Option toEntity(OptionPostDto optionDto);
    @Mapping(target = "questionId", source = "question.questionId")
    OptionDto toDto(Option option);
}
