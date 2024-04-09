package com.example.EnglishAppAPI.entities;

import jakarta.persistence.*;
import lombok.*;

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
    private boolean isRead;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "created_item_id")
    private Long createdItemId;

    @Column(name = "entity_item_id")
    private Long entityItemId;
}
