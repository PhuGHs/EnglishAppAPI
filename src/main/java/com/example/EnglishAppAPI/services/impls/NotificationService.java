package com.example.EnglishAppAPI.services.impls;

import com.example.EnglishAppAPI.entities.Notification;
import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.mapstruct.dtos.NotificationDto;
import com.example.EnglishAppAPI.mapstruct.dtos.NotificationPostDto;
import com.example.EnglishAppAPI.mapstruct.mappers.NotificationMapper;
import com.example.EnglishAppAPI.repositories.NotificationRepository;
import com.example.EnglishAppAPI.repositories.UserRepository;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.responses.ApiResponseStatus;
import com.example.EnglishAppAPI.services.interfaces.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificationService implements INotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final UserRepository userRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, NotificationMapper notificationMapper, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> getUserNotifications(Long currentUserId) {
        if (!userRepository.existsById(currentUserId)) {
            throw new NotFoundException("user is not existed");
        }
        List<Notification> notifications = notificationRepository.getUserNotifications(currentUserId);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get unread notifications", notifications.stream().map(notificationMapper::toDto)));
    }

    @Override
    public ResponseEntity<?> markNotificationsAsRead(Long currentUserId) {
        if (!userRepository.existsById(currentUserId)) {
            throw new NotFoundException("user is not existed");
        }
        List<Notification> notifications = notificationRepository.getUserNotifications(currentUserId);
        for(Notification notification : notifications) {
            notification.setRead(true);
        }
        notifications = notificationRepository.saveAll(notifications);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "mark notifications as read", notifications.stream().map(notificationMapper::toDto)));
    }

    @Override
    public ResponseEntity<?> getUnreadNotifications(Long currentUserId) {
        if (!userRepository.existsById(currentUserId)) {
            throw new NotFoundException("user is not existed");
        }
        List<Notification> notifications = notificationRepository.getUnreadNotifications(currentUserId);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get unread notifications", notifications.stream().map(notificationMapper::toDto)));
    }

    @Override
    public NotificationDto addNotification(NotificationPostDto notificationPostDto) {
        UserEntity sender = userRepository.findById(notificationPostDto.getSenderId())
                .orElseThrow(() -> new NotFoundException("Cant find the sender"));
        UserEntity receiver = userRepository.findById(notificationPostDto.getReceiverId())
                .orElseThrow(() -> new NotFoundException("Cant find the sender"));
        Notification notification = Notification.builder()
                .sender(sender)
                .receiver(receiver)
                .type(notificationPostDto.getType())
                .createdAt(new Date())
                .isRead(false)
                .createdItemId(notificationPostDto.getCreatedItemId())
                .entityItemId(notificationPostDto.getEntityItemId())
                .message(notificationPostDto.getMessage())
                .build();
        notification = notificationRepository.save(notification);
        //send to client
        return notificationMapper.toDto(notification);
    }
}
