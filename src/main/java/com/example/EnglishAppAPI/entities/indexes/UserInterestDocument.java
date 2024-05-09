package com.example.EnglishAppAPI.entities.indexes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "user_interest_index", createIndex = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInterestDocument {
    private Long userId;
    private Long interestId;
}
