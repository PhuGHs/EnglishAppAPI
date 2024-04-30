package com.example.EnglishAppAPI.services.interfaces;

import com.example.EnglishAppAPI.entities.Message;
import com.example.EnglishAppAPI.mapstruct.dtos.ConversationPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.MessagePostDto;
import com.example.EnglishAppAPI.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface IChatService {
    ResponseEntity<?> createConversation(ConversationPostDto conversationPostDto);
    ResponseEntity<?> getAllConversations(Long userId);
    ResponseEntity<?> getAllMessages(Long conversationId);
    ResponseEntity<?> sendMessage(MessagePostDto messagePostDto);
    ResponseEntity<?> getParticipants(Long conversationId);
    ResponseEntity<?> markAsRead(Long messageId);
}
