package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.entities.Message;
import com.example.EnglishAppAPI.mapstruct.dtos.ConversationPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.MessagePostDto;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.services.impls.ChatService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/chat")
@RestControllerAdvice
@Validated
//@SecurityRequirement(name = "bearerAuth")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @PostMapping("/send-message")
    public ResponseEntity<?> sendMessage(@Valid @RequestBody MessagePostDto messagePostDto) {
        return chatService.sendMessage(messagePostDto);
    }

    @PostMapping("/create-room")
    public ResponseEntity<?> createRoom(@Valid @RequestBody ConversationPostDto conversationPostDto) {
        return chatService.createConversation(conversationPostDto);
    }

    @GetMapping("/{userId}/rooms")
    public ResponseEntity<?> getAllRooms(@NotNull(message = "the userId is required") @Valid @PathVariable Long userId) {
        return chatService.getAllConversations(userId);
    }

    @GetMapping("/rooms/{roomId}/messages")
    public ResponseEntity<?> getAllMessages(@NotNull(message = "the messageRoomId is required") @Valid @PathVariable Long messageRoomId) {
        return chatService.getAllMessages(messageRoomId);
    }

    @GetMapping("/rooms/{roomId}/participants")
    public ResponseEntity<?> getParticipants(@NotNull(message = "the messageRoomId is required") @Valid @PathVariable Long messageRoomId) {
        return chatService.getParticipants(messageRoomId);
    }

    @PutMapping("/rooms/messages/mark-as-read")
    public ResponseEntity<?> markAsRead(@NotNull(message = "the messageId is required") @Valid @PathVariable Long messageId) {
        return chatService.markAsRead(messageId);
    }
}
