package com.example.EnglishAppAPI.services.impls;

import com.example.EnglishAppAPI.entities.Message;
import com.example.EnglishAppAPI.entities.MessageRoom;
import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.mapstruct.dtos.ConversationPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.MessagePostDto;
import com.example.EnglishAppAPI.mapstruct.mappers.MessageMapper;
import com.example.EnglishAppAPI.mapstruct.mappers.MessageRoomMapper;
import com.example.EnglishAppAPI.mapstruct.mappers.UserMapper;
import com.example.EnglishAppAPI.repositories.MessageRepository;
import com.example.EnglishAppAPI.repositories.MessageRoomRepository;
import com.example.EnglishAppAPI.repositories.UserRepository;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.responses.ApiResponseStatus;
import com.example.EnglishAppAPI.services.interfaces.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class ChatService implements IChatService {
    @Autowired
    public ChatService(UserRepository userRepository, MessageRoomRepository messageRoomRepository, MessageRoomMapper messageRoomMapper, MessageMapper messageMapper, UserMapper userMapper, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRoomRepository = messageRoomRepository;
        this.messageRoomMapper = messageRoomMapper;
        this.messageMapper = messageMapper;
        this.userMapper = userMapper;
        this.messageRepository = messageRepository;
    }
    private final UserRepository userRepository;
    private final MessageRoomRepository messageRoomRepository;
    private final MessageRoomMapper messageRoomMapper;
    private final MessageMapper messageMapper;
    private final UserMapper userMapper;
    private final MessageRepository messageRepository;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Override
    public ResponseEntity<?> createConversation(ConversationPostDto conversationPostDto) {
        UserEntity sender = userRepository.findById(conversationPostDto.getSenderId())
                .orElse(null);
        if (sender == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("cannot find the sender");
        }
        UserEntity receiver = userRepository.findById(conversationPostDto.getReceiverId())
                .orElse(null);
        if (receiver == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("cannot find the receiver");
        }
        MessageRoom messageRoom = new MessageRoom();
        messageRoom.setRoomName(sender.getFullName() + receiver.getFullName());
        Set<UserEntity> users = new HashSet<>();
        users.add(sender);
        users.add(receiver);
        messageRoom.setUsers(users);
        messageRoomRepository.save(messageRoom);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "create a conversation", messageRoomMapper.toDto(messageRoom)));
    }

    @Override
    public ResponseEntity<?> getAllConversations(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user not found"));
        List<MessageRoom> messageRooms = messageRoomRepository.findByUsersContains(user);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get all user conversations", messageRooms.stream().map(messageRoomMapper::toDto)));
    }

    @Override
    public ResponseEntity<?> getAllMessages(Long conversationId) {
        MessageRoom messageRoom = messageRoomRepository.findById(conversationId)
                .orElseThrow(() -> new NotFoundException("message room is not found"));
        List<Message> messages = messageRepository.findByMessageRoom(messageRoom);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get all messages", messages.stream().map(messageMapper::toDto)));
    }

    @Override
    public ResponseEntity<?> sendMessage(MessagePostDto messagePostDto) throws IOException {
        Message message = messageMapper.toEntity(new MessagePostDto(messagePostDto.getMessageRoomId(), messagePostDto.getSenderId(), messagePostDto.getReceiverId(), messagePostDto.getMessage(), messagePostDto.isRead(), messagePostDto.getCreatedAt(), ""));
        MessageRoom messageRoom = messageRoomRepository.findById(messagePostDto.getMessageRoomId())
                        .orElseThrow(() -> new NotFoundException("message room not found"));
        message = messageRepository.save(message);
        if (!Objects.equals(messagePostDto.getImage(), "")) {
            Map<String, Object> result = cloudinaryService.uploadFile(messagePostDto.getImage(), "MessageRoom-"+ messagePostDto.getMessageRoomId().toString()+"-"+message.getMessageId().toString(), true, true);
            if (!result.containsKey("public_id")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(ApiResponseStatus.FAIL, "Failed to upload image" + result.get("error").toString(), ""));
            }
            String fileUrl = String.format("https://res.cloudinary.com/daszajz9a/image/upload/v%s/%s", result.get("version"), result.get("public_id"));
            message.setImage(fileUrl);
        }
        message = messageRepository.save(message);
        messageRoom.setLastSentMessage(message);
        messageRoom.setLastSentUser(message.getSender());
        messageRoomRepository.save(messageRoom);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "send message", messageMapper.toDto(message)));
    }

    @Override
    public ResponseEntity<?> getParticipants(Long conversationId) {
        MessageRoom messageRoom = messageRoomRepository.findById(conversationId)
                .orElseThrow(() -> new NotFoundException("message room is not found"));
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get participants", messageRoom.getUsers().stream().map(userMapper::toNecessaryDto)));
    }

    @Override
    public ResponseEntity<?> markAsRead(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NotFoundException("message is not found"));
        message.setRead(true);
        messageRepository.save(message);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "mark as read", messageMapper.toDto(message)));
    }
}