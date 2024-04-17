package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.entities.Message;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @PostMapping("/{roomId}/send")
    public ResponseEntity<ApiResponse> sendMessages(@PathVariable Long roomId, @RequestBody Message message) {
        return chatService.sendMessages(roomId, message);
    }
}
