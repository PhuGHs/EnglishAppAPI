package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.MessageRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRoomRepository extends JpaRepository<MessageRoom, Long> {
}
