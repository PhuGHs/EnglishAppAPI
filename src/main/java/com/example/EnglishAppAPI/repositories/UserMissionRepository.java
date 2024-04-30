package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.Mission;
import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.entities.UserMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMissionRepository extends JpaRepository<UserMission, Long> {
    UserMission findByUserAndMission(UserEntity user, Mission mission);
    List<UserMission> findByUser(UserEntity user);
}
