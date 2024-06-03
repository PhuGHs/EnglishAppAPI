package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.LearningRoom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LearningRoomRepository extends JpaRepository<LearningRoom, Long> {
    @Query("select lr from LearningRoom lr where lr.isLive = true order by lr.createdAt")
    List<LearningRoom> getLiveLearningRoom();
    @Query("select lr from LearningRoom lr where lr.scheduledTo >= :startDate and lr.scheduledTo <= :endDate order by lr.scheduledTo")
    List<LearningRoom> getLearningRoomBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    @Query("select lr from LearningRoom lr where lr.isLive = true and lr.topic.englishLevel.levelId <= :levelId and lr.createdAt < current date order by lr.createdAt limit 10")
    List<LearningRoom> suggestRooms(@Param("levelId") Long levelId);
}
