package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.Message;
import com.example.EnglishAppAPI.entities.MessageRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByMessageRoom(MessageRoom messageRoom);
}
