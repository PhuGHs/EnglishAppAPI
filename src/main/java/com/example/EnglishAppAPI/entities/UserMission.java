package com.example.EnglishAppAPI.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.User;

@Entity
@Table(name = "user_mission")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserMission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_mission_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @Column(name = "is_completed")
    private boolean isCompleted = false;

    @Column(name = "completion_count")
    private int completionCount = 0;
}
