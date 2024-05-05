package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.mapstruct.dtos.LearningRoomPostInstantDto;
import com.example.EnglishAppAPI.mapstruct.dtos.LearningRoomPostLaterDto;
import com.example.EnglishAppAPI.mapstruct.dtos.JoinLearningRoomDto;
import com.example.EnglishAppAPI.mapstruct.dtos.PromoteToOwnerDto;
import com.example.EnglishAppAPI.services.impls.LearningRoomService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RestControllerAdvice
@RequestMapping("${api.prefix}/learning-rooms")
@Validated
public class LearningRoomController {
    @Autowired
    private LearningRoomService learningRoomService;
    @PostMapping("/create-instantly")
    public ResponseEntity<?> createLearningRoomInstantly(@Valid @RequestBody LearningRoomPostInstantDto learningRoomPostInstantDto) {
        return learningRoomService.createLearningRoomInstantly(learningRoomPostInstantDto);
    }

    @PostMapping("/schedule-for-the-room")
    public ResponseEntity<?> scheduleTheRoom(@Valid @RequestBody LearningRoomPostLaterDto learningRoomPostLaterDto) {
        return learningRoomService.scheduleLearningRoom(learningRoomPostLaterDto);
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinLearningRoom(@RequestBody @Valid JoinLearningRoomDto joinLearningRoomDto) {
        return learningRoomService.joinLearningRoom(joinLearningRoomDto.getRoomId(), joinLearningRoomDto.getUserId());
    }

    @DeleteMapping("/participants/{participantId}")
    public ResponseEntity<?> kickParticipant(@PathVariable Long participantId) {
        return learningRoomService.kickParticipant(participantId);
    }

    @PutMapping("/promote-to-owner")
    public ResponseEntity<?> promoteToOwner(@RequestBody PromoteToOwnerDto promoteToOwnerDto) {
        return learningRoomService.promoteParticipantToOwner(promoteToOwnerDto.getParticipantId(), promoteToOwnerDto.getOwnerId());
    }

    @GetMapping("/{roomId}/participants")
    public ResponseEntity<?> getParticipants(@PathVariable @NotNull(message = "roomId is required") Long roomId, @RequestParam(defaultValue = "true") boolean isSpeaker) {
        return learningRoomService.getParticipants(roomId, isSpeaker);
    }

    @GetMapping("")
    public ResponseEntity<?> getLearningRoom(@RequestParam boolean isLive, @RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        return learningRoomService.getLearningRooms(startDate, endDate, isLive);
    }

    @PutMapping("/end-room")
    public ResponseEntity<?> endRoom(@RequestBody @Valid JoinLearningRoomDto joinLearningRoomDto) {
        return learningRoomService.endRoom(joinLearningRoomDto.getRoomId(), joinLearningRoomDto.getUserId());
    }
}
