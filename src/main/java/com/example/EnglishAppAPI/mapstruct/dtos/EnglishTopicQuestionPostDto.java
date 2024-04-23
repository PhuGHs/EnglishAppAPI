package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Data
@Getter
@Setter
public class EnglishTopicQuestionPostDto {
    @JsonProperty("topic_id")
    @NotNull(message = "this topic id is required")
    private Long topicId;
    @JsonProperty("question")
    @NotEmpty(message = "The question property is required")
    private String question;
    @JsonProperty("sample_answer")
    @NotEmpty(message = "The answer property is required")
    private String sampleAnswer;
}
