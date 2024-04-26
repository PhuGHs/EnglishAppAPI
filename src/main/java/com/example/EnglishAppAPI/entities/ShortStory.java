package com.example.EnglishAppAPI.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "short_stories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShortStory {
    @Column(name = "short_story_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "title")
    private String title;
    @Column(nullable = false, name = "paragraph")
    private String paragraph;
    @Column(name = "number_of_likes")
    private int numberOfLikes = 0;
    @Column(name = "image")
    private String image;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate = LocalDateTime.now();
}
