package com.example.EnglishAppAPI.entities;

import jakarta.persistence.*;
import lombok.*;

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
    @Column(name = "number_of_likes")
    private int numberOfLikes = 0;
    @Column(name = "image")
    private String image;
}
