package com.example.EnglishAppAPI.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "discussion_topics")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscussionTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "topic")
    private Set<Discussion> discussions = new HashSet<>();
}
