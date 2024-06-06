package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.mapstruct.dtos.*;
import com.example.EnglishAppAPI.services.impls.LearningRoomService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RestControllerAdvice
@RequestMapping("${api.prefix}/learning-rooms")
@SecurityRequirement(name = "bearerAuth")
@Validated
public class LearningRoomController {
    @Autowired
    private LearningRoomService learningRoomService;

    @PostMapping("/create-instantly")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> createLearningRoomInstantly(@Valid @RequestBody LearningRoomPostInstantDto learningRoomPostInstantDto) {
        return learningRoomService.createLearningRoomInstantly(learningRoomPostInstantDto);
    }

    @PostMapping("/schedule-for-the-room")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> scheduleTheRoom(@Valid @RequestBody LearningRoomPostLaterDto learningRoomPostLaterDto) {
        return learningRoomService.scheduleLearningRoom(learningRoomPostLaterDto);
    }

    @PostMapping("/join")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> joinLearningRoom(@RequestBody @Valid JoinLearningRoomDto joinLearningRoomDto) {
        return learningRoomService.joinLearningRoom(joinLearningRoomDto);
    }

    @DeleteMapping("/participants/{participantId}")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> kickParticipant(@PathVariable Long participantId) {
        return learningRoomService.kickParticipant(participantId);
    }

    @PutMapping("/promote-to-owner")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> promoteToOwner(@RequestBody PromoteToOwnerDto promoteToOwnerDto) {
        return learningRoomService.promoteParticipantToOwner(promoteToOwnerDto.getParticipantId(), promoteToOwnerDto.getOwnerId());
    }

    @GetMapping("/{roomId}/participants")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> getParticipants(@PathVariable @NotNull(message = "roomId is required") Long roomId, @RequestParam(defaultValue = "true") boolean isSpeaker) {
        return learningRoomService.getParticipants(roomId, isSpeaker);
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> getLearningRoom(@RequestParam boolean isLive) {
        return learningRoomService.getLearningRooms(isLive);
    }

    @GetMapping("/suggest-rooms")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> suggestLearningRoom(@RequestParam Long currentUserId) {
        return learningRoomService.suggestRooms(currentUserId);
    }

    @GetMapping("/{roomId}/get-messages")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> getMessages(@PathVariable Long roomId) {
        return learningRoomService.getMessages(roomId);
    }

    @GetMapping("/{roomId}/get-password")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> getPassword(@PathVariable Long roomId) {
        return learningRoomService.getRoomPassword(roomId);
    }

    @PutMapping("/end-room")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> toggleSpeaker(@RequestBody @Valid JoinLearningRoomDto joinLearningRoomDto) {
        return learningRoomService.endRoom(joinLearningRoomDto.getRoomId(), joinLearningRoomDto.getUserId());
    }

    @PutMapping("/toggle-speaker")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> endRoom(@RequestBody @Valid ToggleSpeakerDto toggleSpeakerDto) {
        return learningRoomService.toggleSpeaker(toggleSpeakerDto.getParticipantId());
    }

    @PutMapping("/leave-room")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> leaveRoom(@RequestBody @Valid LeaveRoomDTO leaveRoomDTO) {
        return learningRoomService.leaveRoom(leaveRoomDTO.getRoomId(), leaveRoomDTO.getParticipantId());
    }

    @PostMapping("/send-messages")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> sendMessages(@RequestBody @Valid LearningRoomMessagePostDto learningRoomMessagePostDto) {
        return learningRoomService.sendMessages(learningRoomMessagePostDto);
    }
}
