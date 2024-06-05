package com.example.EnglishAppAPI.services.interfaces;

import com.example.EnglishAppAPI.mapstruct.dtos.*;
import jakarta.annotation.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public interface ILearningRoomService {
    ResponseEntity<?> createLearningRoomInstantly(LearningRoomPostInstantDto learningRoomPostInstantDto);
    ResponseEntity<?> scheduleLearningRoom(LearningRoomPostLaterDto learningRoomPostLaterDto);
    ResponseEntity<?> joinLearningRoom(JoinLearningRoomDto joinLearningRoom);
    ResponseEntity<?> kickParticipant(Long participantId);
    ResponseEntity<?> promoteParticipantToOwner(Long participantId, Long ownerParticipantId);
    ResponseEntity<?> getParticipants(Long roomId, boolean isSpeaker);
    ResponseEntity<?> getLearningRooms(boolean isLive);
    ResponseEntity<?> endRoom(Long roomId, Long ownerId);
    ResponseEntity<?> sendMessages(LearningRoomMessagePostDto messagePostDto);
    ResponseEntity<?> suggestRooms(Long currentUserId);
    ResponseEntity<?> leaveRoom(Long roomId, Long participantId);
}
