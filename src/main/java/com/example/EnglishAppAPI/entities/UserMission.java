package com.example.EnglishAppAPI.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

@Entity
@Table(name = "user_mission")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserMission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_mission_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @Column(name = "is_completed")
    private boolean isCompleted;
}
