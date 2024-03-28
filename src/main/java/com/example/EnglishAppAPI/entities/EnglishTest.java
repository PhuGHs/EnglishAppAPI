package com.example.EnglishAppAPI.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "english_tests")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class EnglishTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "english_test_id")
    private Long englishTestId;

    @Column(name = "number_of_questions")
    private int numberOfQuestions;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @OneToMany
    private Set<Question> questions;
}
