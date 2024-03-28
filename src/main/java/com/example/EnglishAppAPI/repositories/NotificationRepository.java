package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Long, Notification> {
}
