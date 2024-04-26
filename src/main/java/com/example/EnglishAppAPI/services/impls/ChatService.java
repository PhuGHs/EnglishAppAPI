package com.example.EnglishAppAPI.services.impls;

import com.example.EnglishAppAPI.entities.Message;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.services.interfaces.IChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ChatService implements IChatService {

    @Override
    public ResponseEntity<ApiResponse> sendMessages(Long roomId, Message message) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> getMessages() {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> updateMessage() {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> deleteMessage() {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> getRooms() {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> findByRoomName() {
        return null;
    }
}
