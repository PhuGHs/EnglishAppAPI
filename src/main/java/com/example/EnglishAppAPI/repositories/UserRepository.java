package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
