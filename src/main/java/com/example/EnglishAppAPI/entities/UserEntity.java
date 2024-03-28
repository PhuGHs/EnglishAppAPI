package com.example.EnglishAppAPI.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "gender")
    private Boolean gender = false;

    @Column(name = "quote")
    private String quote = "";

    @Column(name = "profile_picture")
    private String profilePicture = "";

    @Column(name = "following_count")
    private int followingCount = 0;

    @Column(name = "followers_count")
    private int followersCount = 0;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private EnglishLevel englishLevel;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Discussion> discussions = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Answer> answers = new HashSet<>();



    @ManyToMany
    @JoinTable(
            name = "user_notifications",
            joinColumns = @JoinColumn(name = "sender_id"),
            inverseJoinColumns = @JoinColumn(name = "receiver_id")
    )
    private Set<UserEntity> sentNotifications;

    @ManyToMany(mappedBy = "sentNotifications")
    private Set<UserEntity> receivedNotifications;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_mission", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "mission_id"))
    private Set<Mission> missions = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "participants", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "room_id"))
    private Set<LearningRoom> learningRooms = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_interest",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id")
    )
    private Set<Interest> interests = new HashSet<>();

    public UserEntity(String fullName, Boolean gender) {
        this.fullName = fullName;
        this.gender = gender;
    }

    public UserEntity(String fullName, Boolean gender, String quote) {
        this.fullName = fullName;
        this.gender = gender;
        this.quote = quote;
    }
}
