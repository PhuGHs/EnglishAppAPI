package com.example.EnglishAppAPI.entities;

import com.example.EnglishAppAPI.mapstruct.enums.NotificationType;
import com.example.EnglishAppAPI.mapstruct.serializers.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserEntity sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private UserEntity receiver;

    @Column(name = "message")
    private String message;

    @Column(name = "isRead")
    private boolean isRead = false;

    @Column(name = "created_at")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createdAt = new Date();

    @Column(name = "created_item_id")
    private Long createdItemId;

    @Column(name = "type")
    private NotificationType type;

    @Column(name = "entity_item_id")
    private Long entityItemId;
}
