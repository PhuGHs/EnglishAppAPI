package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BanUserDto {
    @JsonProperty("report_id")
    @NotNull(message = "reportId is required")
    private Long reportId;

    @JsonProperty("reported_user_id")
    @NotNull(message = "reportedUserId is required")
    private Long reportedUserId;
}
