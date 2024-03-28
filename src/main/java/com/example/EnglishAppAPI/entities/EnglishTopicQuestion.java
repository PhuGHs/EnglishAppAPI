package com.example.EnglishAppAPI.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "english_topic_questions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnglishTopicQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_question_id")
    private Long id;

    @Column(name = "question_name")
    private String questionName;

    @Column(name = "sample_answer")
    private String sampleAnswer;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private EnglishTopic topic;
}
