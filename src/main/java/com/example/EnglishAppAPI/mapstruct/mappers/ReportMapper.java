package com.example.EnglishAppAPI.mapstruct.mappers;

import com.example.EnglishAppAPI.entities.Report;
import com.example.EnglishAppAPI.mapstruct.dtos.ReportDto;
import com.example.EnglishAppAPI.mapstruct.dtos.ReportPostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReportMapper {
    @Mapping(source = "reporter.userId", target = "reporterId")
    @Mapping(source = "reported.userId", target = "reportedId")
    ReportDto toDto(Report report);

    @Mapping(target = "reporter.userId", source = "reporterId")
    @Mapping(target = "reported.userId", source = "reportedId")
    Report toEntity(ReportPostDto reportPostDto);
}
