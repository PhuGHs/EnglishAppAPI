package com.example.EnglishAppAPI.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "questions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;
    @Column(name = "question_name")
    private String questionName;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private Set<Option> options = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "english_test_id")
    private EnglishTest englishTest;
}
