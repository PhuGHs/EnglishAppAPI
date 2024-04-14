package com.example.EnglishAppAPI.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "learning_rooms")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LearningRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "learning_room_id")
    private Long id;

    @Column(name = "room_name", nullable = false)
    private String roomName;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "scheduled_to")
    private Date scheduledTo;

    @Column(name = "max_participants", nullable = false)
    private int maxParticipants;

    @ManyToMany(mappedBy = "learningRooms")
    private Set<UserEntity> users = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private EnglishTopic topic;
}
