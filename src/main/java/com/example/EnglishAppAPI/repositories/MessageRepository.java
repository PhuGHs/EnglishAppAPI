package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.Message;
import com.example.EnglishAppAPI.entities.MessageRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE m.messageRoom.messageRoomId = :messageRoomId ORDER BY m.createdAt asc")
    List<Message> findByMessageRoom(@Param("messageRoomId") Long messageRoomId);
}
