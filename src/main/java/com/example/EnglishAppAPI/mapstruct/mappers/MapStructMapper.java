package com.example.EnglishAppAPI.mapstruct.mappers;

import com.example.EnglishAppAPI.entities.Answer;
import com.example.EnglishAppAPI.mapstruct.dtos.AnswerDto;
import com.example.EnglishAppAPI.mapstruct.dtos.AnswerGetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.annotation.processing.Generated;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2021-03-11T19:21:44+0100",
        comments = "version: 1.4.2.Final, compiler: javac, environment: Java 13.0.2 (Oracle Corporation)"
)

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    AnswerGetDto answerToDto(Answer answer);
    Answer answerDtoToEntity(AnswerDto answerDto);
}
