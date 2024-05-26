package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.MessageRoom;
import com.example.EnglishAppAPI.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRoomRepository extends JpaRepository<MessageRoom, Long> {
    @Query("SELECT mr from MessageRoom mr where mr.lastSentMessage.sender.userId = :userId or mr.lastSentMessage.receiver.userId = :userId order by mr.lastSentMessage.createdAt desc")
    List<MessageRoom> findByUsersContains(@Param("userId") Long userId);
    @Query("SELECT mr from MessageRoom mr JOIN mr.users u1 JOIN mr.users u2 " +
            "WHERE u1.userId = :senderId AND u2.userId = :receiverId " +
            "OR u1.userId = :receiverId AND u2.userId = :senderId"
    )
    Optional<MessageRoom> findRoomByUsers(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);
}
