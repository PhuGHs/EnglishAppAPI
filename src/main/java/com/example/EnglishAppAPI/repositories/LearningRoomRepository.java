package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.LearningRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LearningRoomRepository extends JpaRepository<LearningRoom, Long> {
}
