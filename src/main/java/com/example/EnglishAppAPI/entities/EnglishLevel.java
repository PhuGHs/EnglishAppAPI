package com.example.EnglishAppAPI.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "english_levels")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnglishLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "level_id")
    private Long levelId;

    @Column(name = "level_name")
    private String levelName;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "englishLevel")
    private Set<UserEntity> users = new HashSet<>();

    @OneToMany(mappedBy = "englishLevel")
    private Set<EnglishTopic> topics = new HashSet<>();

    public EnglishLevel(String levelName, String description) {
        this.levelName = levelName;
        this.description = description;
    }
}
