package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewPostDto {
    @JsonProperty("user_who_was_reviewed_id")
    @NotNull(message = "the userWhoWasReviewedId is required")
    private Long userWhoWasReviewedId;
    @JsonProperty("user_who_reviewed_id")
    @NotNull(message = "the userWhoReviewedId is required")
    private Long userWhoReviewedId;
    @JsonProperty("star")
    @Range(min = 0, max = 5, message = "rating star must be between 0 and 5")
    private int star;
    @JsonProperty("comment")
    @NotEmpty(message = "the comment field is required")
    private String comment;
    @JsonProperty("created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
