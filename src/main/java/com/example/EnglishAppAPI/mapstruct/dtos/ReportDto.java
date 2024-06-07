package com.example.EnglishAppAPI.mapstruct.dtos;

import com.example.EnglishAppAPI.mapstruct.serializers.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

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
    @JsonProperty("reason")
    private String reason;
    @JsonProperty("reporter")
    private UserNecessaryDto reporter;
    @JsonProperty("reported")
    private UserNecessaryDto reported;
    @JsonProperty("is_solved")
    private boolean isSolved;
    @JsonProperty("created_date")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createdDate;
}
