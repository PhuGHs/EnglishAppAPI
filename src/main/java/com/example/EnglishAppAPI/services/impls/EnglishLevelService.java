package com.example.EnglishAppAPI.services.impls;

import com.example.EnglishAppAPI.entities.EnglishLevel;
import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.repositories.EnglishLevelRepository;
import com.example.EnglishAppAPI.services.interfaces.IEnglishLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EnglishLevelService implements IEnglishLevelService {
    @Autowired
    private EnglishLevelRepository englishLevelRepository;
    @Autowired
    private AccountService accountService;
    @Override
    public ResponseEntity<?> getUsersWhoHasTheSameLevelAsCurrentUser() {
        ResponseEntity<UserEntity> responseEntity = accountService.getCurrentUser();
        UserEntity user = responseEntity.getBody();
        assert user != null;
        EnglishLevel currentUserEnglishLevel = user.getEnglishLevel();
        return ResponseEntity.status(HttpStatus.OK).body(currentUserEnglishLevel.getUsers());
    }

    @Override
    public ResponseEntity<?> insertLevels() {
        List<EnglishLevel> englishLevels = Arrays.asList(
                new EnglishLevel("A1 Elementary", "Description"),
                new EnglishLevel("A2 Pre Intermediate", "Description"),
                new EnglishLevel("B1 Intermediate", "Description"),
                new EnglishLevel("B2 Upper Intermediate", "Description"),
                new EnglishLevel("C1 Advanced", "Description"),
                new EnglishLevel("C2 Proficient", "Description")
        );
        englishLevelRepository.saveAll(englishLevels);
        return ResponseEntity.status(HttpStatus.CREATED).body(englishLevels);
    }
}
