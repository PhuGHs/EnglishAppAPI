package com.example.EnglishAppAPI.entities;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private EnglishTopic topic;

    @OneToMany(mappedBy = "discussion")
    private Set<Answer> answers;
}
