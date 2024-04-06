package com.example.EnglishAppAPI.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "message_rooms")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_room_id")
    private Long messageRoomId;

    @Column(name = "room_name")
    private String roomName;

    @ManyToMany
    @JoinTable(name = "user_message_rooms",
            joinColumns = @JoinColumn(name = "message_room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserEntity> users;

    @OneToMany(mappedBy = "messageRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Message> messages;
}
