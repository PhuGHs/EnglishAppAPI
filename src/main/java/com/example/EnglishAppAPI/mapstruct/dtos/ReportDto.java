package com.example.EnglishAppAPI.mapstruct.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ReportDto {
    @NotEmpty(message = "image may not be empty")
    private String evidenceImage;
    @NotEmpty(message = "content may not be empty")
    private String content;
    @NotNull(message = "reporter may not null")
    private Long reporterId;
    @NotNull(message = "user who get reported id may not null")
    private Long reportedId;
}
