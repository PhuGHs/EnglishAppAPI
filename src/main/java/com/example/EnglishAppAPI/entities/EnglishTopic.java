package com.example.EnglishAppAPI.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "english_topics")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnglishTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private long topicId;

    @Column(name = "header")
    private String header;

    @Column(name = "content")
    private String content;

    @OneToMany(mappedBy = "topic")
    private Set<Discussion> discussions = new HashSet<>();

    @OneToMany(mappedBy = "topic")
    private Set<EnglishTopicQuestion> questions = new HashSet<>();

    @OneToMany(mappedBy = "topic")
    private Set<LearningRoom> learningRooms = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "level_id")
    private EnglishLevel englishLevel;

    public EnglishTopic(String header, String content) {
        this.header = header;
        this.content = content;
    }
}
