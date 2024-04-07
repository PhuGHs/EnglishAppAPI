package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.LearningRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearningRoomRepository extends JpaRepository<LearningRoom, Long> {
}
