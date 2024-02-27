package com.example.EnglishAppAPI.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name = "english_topics")
public class EnglishTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long topicId;
    @ManyToOne
    @JoinColumn(name = "levelId")
    private EnglishLevel englishLevel;
    private String header;
    private String content;

    public EnglishTopic(String header, String content) {
        this.header = header;
        this.content = content;
    }
}
