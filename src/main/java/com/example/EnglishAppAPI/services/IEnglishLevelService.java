package com.example.EnglishAppAPI.services;

import com.example.EnglishAppAPI.entities.EnglishLevel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface IEnglishLevelService {
    ResponseEntity<?> getUsersWhoHasTheSameLevelAsCurrentUser();
    ResponseEntity<?> insertLevels();
}
