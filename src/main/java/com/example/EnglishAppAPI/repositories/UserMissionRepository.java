package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.UserMission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMissionRepository extends JpaRepository<Long, UserMission> {
}
