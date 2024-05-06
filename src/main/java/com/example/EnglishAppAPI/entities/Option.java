package com.example.EnglishAppAPI.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "options")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Option {
    @Id
    @Column(name = "option_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "option_name")
    private String optionName;
    @Column(name = "is_correct")
    private boolean isCorrect;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
