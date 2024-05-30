package com.example.EnglishAppAPI.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Field;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DiscussionTopicDto {
    @JsonProperty("id")
    @Field(name = "id")
    private Long id;
    @JsonProperty("name")
    @Field(name = "name")
    private String name;
}
