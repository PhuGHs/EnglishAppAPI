package com.example.EnglishAppAPI.mapstruct.mappers;

import com.example.EnglishAppAPI.entities.Report;
import com.example.EnglishAppAPI.mapstruct.dtos.ReportDto;
import com.example.EnglishAppAPI.mapstruct.dtos.ReportPostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ReportMapper {
    @Mapping(source = "reporter", target = "reporter")
    @Mapping(source = "reported", target = "reported")
    @Mapping(source = "reason", target = "reason")
    ReportDto toDto(Report report);

    @Mapping(target = "reporter.userId", source = "reporterId")
    @Mapping(target = "reported.userId", source = "reportedId")
    @Mapping(target = "reason", source = "reason")
    Report toEntity(ReportPostDto reportPostDto);
}
