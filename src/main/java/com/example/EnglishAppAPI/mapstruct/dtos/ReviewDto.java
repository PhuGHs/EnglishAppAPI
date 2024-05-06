package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewDto {
    @JsonProperty("review_id")
    private Long reviewId;
    @JsonProperty("user_who_was_reviewed")
    private UserNecessaryDto userWhoWasReviewed;
    @JsonProperty("user_who_reviewed")
    private UserNecessaryDto userWhoReviewed;
    @JsonProperty("star")
    private int star;
    @JsonProperty("comment")
    private String comment;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
