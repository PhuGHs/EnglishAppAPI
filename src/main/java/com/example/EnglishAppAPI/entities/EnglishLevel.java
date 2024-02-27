package com.example.EnglishAppAPI.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name = "english_levels")
public class EnglishLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long levelId;
    private String levelName;
    private String description;

    public EnglishLevel(String levelName, String description) {
        this.levelName = levelName;
        this.description = description;
    }
}
