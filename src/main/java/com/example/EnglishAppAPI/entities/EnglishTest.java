package com.example.EnglishAppAPI.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "english_tests")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnglishTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "english_test_id")
    private Long englishTestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id")
    private EnglishLevel englishLevel;

    @Column(name = "number_of_questions")
    private int numberOfQuestions;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "englishTest", cascade = CascadeType.ALL)
    private Set<Question> questions;
}
