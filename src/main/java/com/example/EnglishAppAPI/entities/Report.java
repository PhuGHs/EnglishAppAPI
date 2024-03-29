package com.example.EnglishAppAPI.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reports")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "evidence_image")
    private String evidenceImage;

    @Column(name = "content")
    private String content;

    @Column(name = "is_solved")
    private boolean isSolved;
}
