package com.example.EnglishAppAPI.entities;

import com.example.EnglishAppAPI.mapstruct.serializers.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;
    @ManyToOne
    @JoinColumn(name = "user_who_was_reviewed_id")
    private UserEntity userWhoWasReviewed;
    @ManyToOne
    @JoinColumn(name = "user_who_reviewed_id")
    private UserEntity userWhoReviewed;
    @Column(name = "star")
    private int star;
    @Column(name = "comment")
    private String comment;
    @Column(name = "created_at")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createdAt = new Date();
}
