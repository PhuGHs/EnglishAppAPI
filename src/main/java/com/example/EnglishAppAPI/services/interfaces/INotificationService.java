package com.example.EnglishAppAPI.services.interfaces;

import com.example.EnglishAppAPI.entities.Notification;
import com.example.EnglishAppAPI.mapstruct.dtos.NotificationDto;
import com.example.EnglishAppAPI.mapstruct.dtos.NotificationPostDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface INotificationService {
    ResponseEntity<?> getUserNotifications(Long currentUserId);
    ResponseEntity<?> markNotificationsAsRead(Long currentUserId);
    ResponseEntity<?> getUnreadNotifications(Long currentUserId);
    NotificationDto addNotification(NotificationPostDto notificationPostDto);
}
