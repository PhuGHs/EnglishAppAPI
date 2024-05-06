package com.example.EnglishAppAPI.mapstruct.mappers;

import com.example.EnglishAppAPI.entities.Question;
import com.example.EnglishAppAPI.mapstruct.dtos.QuestionDto;
import com.example.EnglishAppAPI.mapstruct.dtos.QuestionPostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OptionMapper.class})
public interface QuestionMapper {
    @Mapping(source = "options", target = "options")
    @Mapping(source = "englishTestId", target = "englishTest.englishTestId")
    Question toEntity(QuestionPostDto questionPostDto);

    @Mapping(source = "options", target = "options")
    @Mapping(target = "englishTestId", source = "englishTest.englishTestId")
    QuestionDto toDto(Question question);
}
