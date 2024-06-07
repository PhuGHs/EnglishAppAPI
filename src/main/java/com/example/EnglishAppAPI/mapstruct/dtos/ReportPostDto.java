package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportPostDto {
    @JsonProperty("evidence_image")
    @NotEmpty(message = "image may not be empty")
    private String evidenceImage;
    @JsonProperty("content")
    @NotEmpty(message = "content may not be empty")
    private String content;
    @JsonProperty("reason")
    @NotEmpty(message = "reason may not be empty")
    private String reason;
    @JsonProperty("reporter_id")
    @NotNull(message = "reporter may not null")
    private Long reporterId;
    @JsonProperty("reported_id")
    @NotNull(message = "user who get reported id may not null")
    private Long reportedId;
}
