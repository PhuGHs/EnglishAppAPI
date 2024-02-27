package com.example.EnglishAppAPI.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private long userId;
    @ManyToOne
    @JoinColumn(name = "levelId")
    private EnglishLevel englishLevel;
    private String fullName;
    private Boolean gender;
    private String quote;
    private String profilePicture = "";
    private int followingCount = 0;
    private int followersCount = 0;
    public UserEntity(String fullName, Boolean gender, String quote, String profilePicture) {
        this.fullName = fullName;
        this.gender = gender;
        this.quote = quote;
        this.profilePicture = profilePicture;
    }

    public UserEntity(String fullName, Boolean gender, String quote) {
        this.fullName = fullName;
        this.gender = gender;
        this.quote = quote;
    }
}
