package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.services.impls.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RestControllerAdvice
@RequestMapping("${api.prefix}/notifications")
@Validated
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/{userId}/get-all")
    public ResponseEntity<?> getUserNotifications(@PathVariable Long userId) {
        return notificationService.getUserNotifications(userId);
    }

    @GetMapping("/{userId}/get-unread")
    public ResponseEntity<?> getUnreadNotifications(@PathVariable Long userId) {
        return notificationService.getUnreadNotifications(userId);
    }

    @PutMapping("/{userId}/mark-all-as-read")
    public ResponseEntity<?> markAllAsRead(@PathVariable Long userId) {
        return notificationService.markNotificationsAsRead(userId);
    }
}
