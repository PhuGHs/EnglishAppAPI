package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("evidence_image")
    private String evidenceImage;
    @JsonProperty("content")
    private String content;
    @JsonProperty("reporter_id")
    private Long reporterId;
    @JsonProperty("reported_id")
    private Long reportedId;
    @JsonProperty("is_solved")
    private boolean isSolved;
    @JsonProperty("created_date")
    private LocalDateTime createdDate;
}
