package com.example.EnglishAppAPI.services.impls;

import com.example.EnglishAppAPI.entities.*;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.mapstruct.dtos.*;
import com.example.EnglishAppAPI.mapstruct.enums.NotificationType;
import com.example.EnglishAppAPI.mapstruct.mappers.LearningRoomMapper;
import com.example.EnglishAppAPI.mapstruct.mappers.ParticipantMapper;
import com.example.EnglishAppAPI.repositories.*;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.responses.ApiResponseStatus;
import com.example.EnglishAppAPI.services.interfaces.ILearningRoomService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class LearningRoomService implements ILearningRoomService {
    private final ParticipantRepository participantRepository;
    private final LearningRoomRepository learningRoomRepository;
    private final UserRepository userRepository;
    private final EnglishTopicRepository englishTopicRepository;
    private final LearningRoomMapper learningRoomMapper;
    private final ParticipantMapper participantMapper;
    private final TaskScheduler taskScheduler;
    private final LearningRoomMessageRepository learningRoomMessageRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final NotificationService notificationService;
    private final EmailService emailService;
    private final AccountRepository accountRepository;
    @Autowired
    public LearningRoomService(ParticipantRepository participantRepository, LearningRoomRepository learningRoomRepository, UserRepository userRepository, EnglishTopicRepository englishTopicRepository, LearningRoomMapper learningRoomMapper, ParticipantMapper participantMapper, TaskScheduler taskScheduler, LearningRoomMessageRepository learningRoomMessageRepository, SimpMessagingTemplate simpMessagingTemplate, NotificationService notificationService, EmailService emailService, AccountRepository accountRepository) {
        this.participantRepository = participantRepository;
        this.learningRoomRepository = learningRoomRepository;
        this.userRepository = userRepository;
        this.englishTopicRepository = englishTopicRepository;
        this.learningRoomMapper = learningRoomMapper;
        this.participantMapper = participantMapper;
        this.taskScheduler = taskScheduler;
        this.learningRoomMessageRepository = learningRoomMessageRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.notificationService = notificationService;
        this.emailService = emailService;
        this.accountRepository = accountRepository;
    }

    @Override
    public ResponseEntity<?> createLearningRoomInstantly(LearningRoomPostInstantDto learningRoomPostDto) {
        UserEntity owner = userRepository.findById(learningRoomPostDto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        EnglishTopic topic = englishTopicRepository.findById(learningRoomPostDto.getEnglishTopicId())
                .orElseThrow(() -> new NotFoundException("Topic not found"));
        LearningRoom learningRoom = LearningRoom.builder()
                .createdAt(new Date())
                .scheduledTo(null)
                .roomName(learningRoomPostDto.getRoomName())
                .maxParticipants(learningRoomPostDto.getMaxParticipants())
                .duration(learningRoomPostDto.getDuration())
                .isLive(true)
                .isPrivate(learningRoomPostDto.isPrivate())
                .password(learningRoomPostDto.getPassword())
                .owner(owner)
                .topic(topic)
                .build();

        Participant ownerParticipant = Participant.builder()
                .room(learningRoom)
                .user(owner)
                .joinTime(new Date())
                .isOwner(true)
                .isSpeaker(true)
                .build();
        Set<Participant> ps = new HashSet<>();
        ps.add(ownerParticipant);
        learningRoom.setParticipants(ps);
        learningRoom = learningRoomRepository.save(learningRoom);
        Long roomId = learningRoom.getId();

        for (UserEntity us : owner.getFollowers()) {
            NotificationDto notificationDto = notificationService.addNotification(new NotificationPostDto(owner.getUserId(), us.getUserId(), owner.getFullName() + " the one you are following, created a learning room! Come join with him", false, NotificationType.LEARNINGROOM , roomId, roomId));
            simpMessagingTemplate.convertAndSend("topic/user/notification/" + us.getUserId(), notificationDto);
        }

        //handle duration
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(learningRoom.getCreatedAt());
        calendar.add(Calendar.HOUR, learningRoomPostDto.getDuration());
        Instant endedInstant = calendar.getTime().toInstant();
        taskScheduler.schedule(() -> {
            LearningRoom learningRoom1 = learningRoomRepository.findById(roomId)
                            .orElseThrow(() -> new NotFoundException("Can't find the room"));
            learningRoom1.setLive(false);
            learningRoomRepository.save(learningRoom1);
            simpMessagingTemplate.convertAndSend("topic/learning-room/" + roomId, new WebsocketType<>("end", "room time has exceeded the duration"));
        }, endedInstant);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "created learning room instantly", learningRoomMapper.toDto(learningRoom)));
    }

    @Override
    public ResponseEntity<?> scheduleLearningRoom(LearningRoomPostLaterDto learningRoomPostLaterDto) {
        UserEntity owner = userRepository.findById(learningRoomPostLaterDto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        EnglishTopic topic = englishTopicRepository.findById(learningRoomPostLaterDto.getEnglishTopicId())
                .orElseThrow(() -> new NotFoundException("Topic not found"));

        LearningRoom learningRoom = LearningRoom.builder()
                .createdAt(new Date())
                .scheduledTo(learningRoomPostLaterDto.getScheduledTo())
                .roomName(learningRoomPostLaterDto.getRoomName())
                .duration(learningRoomPostLaterDto.getDuration())
                .isLive(false)
                .maxParticipants(learningRoomPostLaterDto.getMaxParticipants())
                .password(learningRoomPostLaterDto.getPassword())
                .isPrivate(learningRoomPostLaterDto.isPrivate())
                .topic(topic)
                .owner(owner)
                .build();

        //schedule to notify user
        Date scheduledTo = learningRoomPostLaterDto.getScheduledTo();
        Instant scheduledInstant = scheduledTo.toInstant();
        Long roomId = learningRoom.getId();
        String email = accountRepository.findByUserId(owner.getUserId()).getEmail();
        taskScheduler.schedule(() -> {
            emailService.sendEmail(email, "Notify about the learning room you scheduled before", "Recently, you have scheduled a learning room, so it is the time right now, come to host the room!");
            NotificationDto notificationDto = notificationService.addNotification(new NotificationPostDto(owner.getUserId(), owner.getUserId(), "You have recently scheduled a learning room, so it is the time right now, come to host the room!", false, NotificationType.LEARNINGROOM , roomId, roomId));
            simpMessagingTemplate.convertAndSend("topic/user/notification/" + owner.getUserId(), notificationDto);
        }, scheduledInstant);


        //schedule to notify room has ended
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(learningRoom.getCreatedAt());
        calendar.add(Calendar.HOUR, learningRoomPostLaterDto.getDuration());
        Instant endedInstant = calendar.getTime().toInstant();
        taskScheduler.schedule(() -> {
            LearningRoom learningRoom1 = learningRoomRepository.findById(roomId)
                    .orElseThrow(() -> new NotFoundException("Can't find the room"));
            learningRoom1.setLive(false);
            learningRoomRepository.save(learningRoom1);
            simpMessagingTemplate.convertAndSend("topic/learning-room/" + roomId, new WebsocketType<>("end", "room time has exceeded the duration"));
        }, endedInstant);
        learningRoom = learningRoomRepository.save(learningRoom);

        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "created learning room later", learningRoomMapper.toDto(learningRoom)));
    }

    @Override
    public ResponseEntity<?> joinLearningRoom(JoinLearningRoomDto joinLearningRoom) {
        Long roomId = joinLearningRoom.getRoomId();
        Long userId = joinLearningRoom.getUserId();
        LearningRoom learningRoom = learningRoomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("learning room not found"));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user not found"));
        if (participantRepository.checkIfExistedInAnotherRoom(userId, roomId)) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ApiResponse(ApiResponseStatus.FAIL, "you are not able to join multiple room at once", ""));
        }
        if (learningRoom.getTopic().getEnglishLevel().getLevelId() > user.getEnglishLevel().getLevelId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(ApiResponseStatus.FAIL, "your level is not suitable for this room", "level"));
        }
        if (learningRoom.isPrivate()) {
            if (!Objects.equals(learningRoom.getPassword(), joinLearningRoom.getPassword())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(ApiResponseStatus.FAIL, "password is not correct", "password"));
            }
        }
        Participant participant = Participant.builder()
                .joinTime(new Date())
                .room(learningRoom)
                .user(user)
                .isSpeaker(false)
                .isOwner(false)
                .build();
        learningRoom.getParticipants().add(participant);
        learningRoom = learningRoomRepository.save(learningRoom);
        simpMessagingTemplate.convertAndSend("/topic/learning-room/" + roomId, new WebsocketType<>("join", participantMapper.toDto(participant)));
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "joined the room", learningRoomMapper.toDto(learningRoom)));
    }

    @Override
    public ResponseEntity<?> kickParticipant(Long participantId) {
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new NotFoundException("participant not found"));
        //send to client
        simpMessagingTemplate.convertAndSend("/topic/learning-room/" + participant.getRoom().getId(), new WebsocketType<>("kick", participantMapper.toDto(participant)));
        participantRepository.delete(participant);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "kick participant", ""));
    }

    @Override
    public ResponseEntity<?> promoteParticipantToOwner(Long participantId, Long ownerParticipantId) {
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new NotFoundException("participant not found"));
        Participant owner = participantRepository.findById(ownerParticipantId)
                .orElseThrow(() -> new NotFoundException("owner not found"));
        participant.setOwner(true);
        owner.setOwner(false);
        participant = participantRepository.save(participant);
        simpMessagingTemplate.convertAndSend("/topic/learning-room/" + participant.getRoom().getId(), new WebsocketType<>("promote", participantMapper.toDto(participant)));
        simpMessagingTemplate.convertAndSend("/topic/learning-room/" + participant.getRoom().getId(), new WebsocketType<>("promote", participantMapper.toDto(owner)));
        participantRepository.save(owner);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "promoted the participant to owner", participantMapper.toDto(participant)));
    }

    @Override
    public ResponseEntity<?> getParticipants(Long roomId, boolean isSpeaker) {
        LearningRoom learningRoom = learningRoomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("learning room not found"));
        Set<Participant> participants = learningRoom.getParticipants();
        if (isSpeaker) {
            participants.removeIf(participant -> !participant.isSpeaker());
        } else {
            participants.removeIf(Participant::isSpeaker);
        }
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get participants", participants.stream().map(participantMapper::toDto)));
    }

    @Override
    public ResponseEntity<?> getLearningRooms(boolean isLive) {
        List<LearningRoom> learningRooms = null;
        if (isLive) {
            learningRooms = learningRoomRepository.getLiveLearningRoom();
            return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get live room", learningRooms.stream().map(learningRoomMapper::toDto)));
        }
        learningRooms = learningRoomRepository.getScheduleRooms();
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get room between", learningRooms.stream().map(learningRoomMapper::toDto)));
    }

    @Override
    public ResponseEntity<?> endRoom(Long roomId, Long ownerId) {
        Participant participant = participantRepository.findById(ownerId)
                .orElseThrow(() -> new NotFoundException("participant not found"));
        if (!participant.isOwner()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("you are not the owner");
        }
        LearningRoom learningRoom = learningRoomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("learning room is not found"));
        if (learningRoom.isLive()) {
            learningRoom.setLive(false);
            learningRoomRepository.save(learningRoom);
        }
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "end room", learningRoomMapper.toDto(learningRoom)));
    }

    @Override
    public ResponseEntity<?> sendMessages(LearningRoomMessagePostDto messagePostDto) {
        Long userId = messagePostDto.getUserId();
        Long roomId = messagePostDto.getRoomId();
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("cant find the user"));
        LearningRoom room = learningRoomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("cant find the room"));
        if (!participantRepository.checkIfExistedInAnotherRoom(userId, roomId)) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ApiResponse(ApiResponseStatus.FAIL, "You are not allowed to send messages in this room because you have not entered yet", ""));
        }
        LearningRoomMessage message = LearningRoomMessage.builder()
                .message(messagePostDto.getMessage())
                .user(user)
                .learningRoom(room)
                .createdAt(new Date())
                .build();
        message = learningRoomMessageRepository.save(message);
        simpMessagingTemplate.convertAndSend("/topic/learning-room/message/" + roomId, learningRoomMapper.toMessageDto(message));
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "message sent", learningRoomMapper.toMessageDto(message)));
    }

    @Override
    public ResponseEntity<?> getMessages(Long roomId) {
        List<LearningRoomMessage> messages = learningRoomMessageRepository.getMessages(roomId);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get messages", messages.stream().map(learningRoomMapper::toMessageDto)));
    }

    @Override
    public ResponseEntity<?> suggestRooms(Long currentUserId) {
        EnglishLevel englishLevel = userRepository.findById(currentUserId)
                .orElseThrow(() -> new NotFoundException("user not found")).getEnglishLevel();
        List<LearningRoom> learningRooms = learningRoomRepository.suggestRooms(englishLevel.getLevelId());
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get rooms", learningRooms.stream().map(learningRoomMapper::toDto)));
    }

    @Override
    public ResponseEntity<?> leaveRoom(Long roomId, Long participantId) {
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new NotFoundException("participant not found"));
        LearningRoom room = learningRoomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("room not found"));
        if (room.getParticipants().size() <= 1) {
            room.setLive(false);
            learningRoomRepository.save(room);
            participantRepository.deleteAParticipant(participantId);
            return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "leave room", ""));
        }
        if (participant.isOwner()) {
            Optional<Participant> optionalParticipant = room.getParticipants().stream().findFirst();
            Participant x = optionalParticipant.get();
            x.setOwner(true);
            x = participantRepository.save(x);
            simpMessagingTemplate.convertAndSend("/topic/learning-room/" + roomId, new WebsocketType<>("leave", participantMapper.toDto(x)));
        } else {
            simpMessagingTemplate.convertAndSend("/topic/learning-room/" + roomId, new WebsocketType<>("leave", participantMapper.toDto(participant)));
        }
        participantRepository.deleteAParticipant(participantId);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "leave room", ""));
    }

    @Override
    public ResponseEntity<?> toggleSpeaker(Long participantId) {
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new NotFoundException("cant find the participant"));
        participant.setSpeaker(!participant.isSpeaker());
        participant = participantRepository.save(participant);
        Long roomId = participant.getRoom().getId();
        simpMessagingTemplate.convertAndSend("/topic/learning-room/" + roomId, new WebsocketType<>("toggle", participantMapper.toDto(participant)));
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "Toggle", participantMapper.toDto(participant)));
    }

    @Override
    public ResponseEntity<?> getRoomPassword(Long roomId) {
        LearningRoom learningRoom = learningRoomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("ROOM NOT FOUND"));
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get password", learningRoom.getPassword()));
    }
}
