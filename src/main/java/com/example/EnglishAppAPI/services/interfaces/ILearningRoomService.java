package com.example.EnglishAppAPI.services.interfaces;

import com.example.EnglishAppAPI.mapstruct.dtos.LearningRoomPostInstantDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface ILearningRoomService {
    ResponseEntity<?> createLearningRoomInstantly(LearningRoomPostInstantDto learningRoomPostInstantDto);
    ResponseEntity<?> scheduleLearningRoom();
    ResponseEntity<?> joinLearningRoom();
    ResponseEntity<?> kickParticipant();
    ResponseEntity<?> promoteParticipantToOwner();
    ResponseEntity<?> getParticipants();
}
