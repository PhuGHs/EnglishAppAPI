package com.example.EnglishAppAPI.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "interests")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Long missionId;

    @Column(name = "mission_name")
    private String missionName;

    @Column(name = "points_awarded")
    private int pointsAwarded;

    @Column(name = "mission_date")
    private Date missionDate;

    @ManyToMany(mappedBy = "missions")
    private Set<UserEntity> users = new HashSet<>();
}
