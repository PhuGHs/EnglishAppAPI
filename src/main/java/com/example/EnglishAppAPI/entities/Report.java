package com.example.EnglishAppAPI.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @ManyToOne
    @JoinColumn(name = "reporter_id")
    private UserEntity reporter;

    @ManyToOne
    @JoinColumn(name = "reported_id")
    private UserEntity reported;

    @Column(name = "evidence_image")
    private String evidenceImage;

    @Column(name = "content")
    private String content;

    @Column(name = "is_solved")
    private boolean isSolved = false;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate = LocalDateTime.now();
}
