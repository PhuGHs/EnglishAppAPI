package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.entities.Message;
import com.example.EnglishAppAPI.mapstruct.dtos.ConversationPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.MessagePostDto;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.services.impls.ChatService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("${api.prefix}/chat")
@RestControllerAdvice
@Validated
@SecurityRequirement(name = "bearerAuth")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @PostMapping("/send-message")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> sendMessage(@Valid @RequestBody MessagePostDto messagePostDto) throws IOException {
        return chatService.sendMessage(messagePostDto);
    }

    @PostMapping("/create-room")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> createRoom(@Valid @RequestBody ConversationPostDto conversationPostDto) {
        return chatService.createConversation(conversationPostDto);
    }

    @GetMapping("/{userId}/rooms")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> getAllRooms(@NotNull(message = "the userId is required") @Valid @PathVariable Long userId) {
        return chatService.getAllConversations(userId);
    }

    @GetMapping("/rooms/{roomId}/messages")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> getAllMessages(@NotNull(message = "the messageRoomId is required") @Valid @PathVariable Long roomId) {
        return chatService.getAllMessages(roomId);
    }

    @GetMapping("/rooms/{roomId}/participants")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> getParticipants(@NotNull(message = "the messageRoomId is required") @Valid @PathVariable Long roomId) {
        return chatService.getParticipants(roomId);
    }

    @PutMapping("/rooms/messages/{messageId}/mark-as-read")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> markAsRead(@NotNull(message = "the messageId is required") @Valid @PathVariable Long messageId) {
        return chatService.markAsRead(messageId);
    }
}
