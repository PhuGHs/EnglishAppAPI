package com.example.EnglishAppAPI.entities;

import com.example.EnglishAppAPI.mapstruct.serializers.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Table(name = "learning_room_messages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LearningRoomMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "learning_room_message_id")
    private Long id;
    @Column(name = "message")
    private String message;
    @Column(name = "image")
    private String image;
    @Column(name = "created_at")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createdAt = new Date();
    @ManyToOne
    @JoinColumn(name = "learning_room_id")
    private LearningRoom learningRoom;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
