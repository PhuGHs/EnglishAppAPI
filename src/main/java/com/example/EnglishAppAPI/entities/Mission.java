package com.example.EnglishAppAPI.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "missions")
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

    @ManyToMany(mappedBy = "missions", fetch = FetchType.LAZY)
    private Set<UserEntity> users = new HashSet<>();

    @Column(name = "max_completion_count")
    private int maxCompletionCount;

    public Mission(String missionName, int pointsAwarded, int maxCompletionCount) {
        this.missionName = missionName;
        this.pointsAwarded = pointsAwarded;
        this.maxCompletionCount = maxCompletionCount;
    }
}
