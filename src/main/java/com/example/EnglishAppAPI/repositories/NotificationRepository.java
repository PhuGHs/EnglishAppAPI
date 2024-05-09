package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.Notification;
import com.example.EnglishAppAPI.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE n.receiver.userId = :receiverId AND n.isRead = false")
    List<Notification> getUnreadNotifications(@Param("receiverId") Long receiverId);
    @Query("SELECT n FROM Notification n WHERE n.receiver.userId = :currentUserId")
    List<Notification> getUserNotifications(@Param("currentUserId") Long currentUserId);
}
