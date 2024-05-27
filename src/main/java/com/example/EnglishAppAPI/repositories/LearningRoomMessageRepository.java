package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.LearningRoomMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearningRoomMessageRepository extends JpaRepository<LearningRoomMessage, Long> {
    @Query("SELECT n from LearningRoomMessage n where n.learningRoom.id = :roomId order by n.createdAt asc")
    List<LearningRoomMessage> getMessages(@Param("roomId") Long roomId);
}
