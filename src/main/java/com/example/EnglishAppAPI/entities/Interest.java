package com.example.EnglishAppAPI.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "interests")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interest_id")
    private Long interestId;

    @Column(name = "interest_name")
    private String interestName;

    @JsonIgnore
    @ManyToMany(mappedBy = "interests")
    private Set<UserEntity> users = new HashSet<>();

    public Interest(String interestName) {
        this.interestName = interestName;
    }
}
