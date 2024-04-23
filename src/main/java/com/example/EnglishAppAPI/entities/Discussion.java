package com.example.EnglishAppAPI.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "discussions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Discussion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discussion_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "number_of_answers")
    private int numberOfAnswers = 0;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private EnglishTopic topic;

    @OneToMany(mappedBy = "discussion")
    private Set<Answer> answers;
}
