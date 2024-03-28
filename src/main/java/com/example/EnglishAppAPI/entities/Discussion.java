package com.example.EnglishAppAPI.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "discussions")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Discussion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discussion_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private EnglishTopic topic;

    @OneToMany(mappedBy = "discussion")
    private Set<Answer> answers;
}
