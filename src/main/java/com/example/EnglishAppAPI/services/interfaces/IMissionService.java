package com.example.EnglishAppAPI.services.interfaces;

import com.example.EnglishAppAPI.mapstruct.dtos.UserMissionPostDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface IMissionService {
    ResponseEntity<?> getUserMissions(Long userId);
    ResponseEntity<?> updateMission(UserMissionPostDto userMissionDto);
    ResponseEntity<?> refreshMissions();
    ResponseEntity<?> addMissions(Long userId);
}
