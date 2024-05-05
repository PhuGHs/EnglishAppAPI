package com.example.EnglishAppAPI.services.interfaces;

import com.example.EnglishAppAPI.mapstruct.dtos.LearningRoomPostInstantDto;
import com.example.EnglishAppAPI.mapstruct.dtos.LearningRoomPostLaterDto;
import jakarta.annotation.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public interface ILearningRoomService {
    ResponseEntity<?> createLearningRoomInstantly(LearningRoomPostInstantDto learningRoomPostInstantDto);
    ResponseEntity<?> scheduleLearningRoom(LearningRoomPostLaterDto learningRoomPostLaterDto);
    ResponseEntity<?> joinLearningRoom(Long roomId, Long userId);
    ResponseEntity<?> kickParticipant(Long participantId);
    ResponseEntity<?> promoteParticipantToOwner(Long participantId, Long ownerParticipantId);
    ResponseEntity<?> getParticipants(Long roomId, boolean isSpeaker);
    ResponseEntity<?> getLearningRooms(@Nullable LocalDateTime startDate, @Nullable LocalDateTime endDate, boolean isLive);
    ResponseEntity<?> endRoom(Long roomId, Long ownerId);
}
