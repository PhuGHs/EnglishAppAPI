package com.example.EnglishAppAPI.services;

import com.example.EnglishAppAPI.entities.Message;
import com.example.EnglishAppAPI.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface IChatService {
    ResponseEntity<ApiResponse> sendMessages(Long roomId, Message message);
    ResponseEntity<ApiResponse> getMessages();
    ResponseEntity<ApiResponse> updateMessage();
    ResponseEntity<ApiResponse> deleteMessage();
    ResponseEntity<ApiResponse> getRooms();
    ResponseEntity<ApiResponse> findByRoomName();
}
