package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.UserMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMissionRepository extends JpaRepository<UserMission, Long> {
}
