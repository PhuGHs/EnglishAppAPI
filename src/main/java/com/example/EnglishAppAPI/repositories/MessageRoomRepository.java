package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.MessageRoom;
import com.example.EnglishAppAPI.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRoomRepository extends JpaRepository<MessageRoom, Long> {
    List<MessageRoom> findByUsersContains(UserEntity user);
}
