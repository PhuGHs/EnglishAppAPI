package com.example.EnglishAppAPI.entities;

import com.example.EnglishAppAPI.mapstruct.serializers.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createdAt = new Date();

    @Column(name = "scheduled_to", nullable = true)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date scheduledTo;

    @Column(name = "duration")
    private int duration;

    @Column(name = "max_participants", nullable = false)
    private int maxParticipants;

    @Column(name = "is_live", nullable = false)
    private boolean isLive;

    @Column(name = "is_private")
    private boolean isPrivate;

    @Column(name = "password", nullable = true)
    private String password;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Participant> participants = new HashSet<>();

    @OneToMany(mappedBy = "learningRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<LearningRoomMessage> messages = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity owner;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private EnglishTopic topic;
}
