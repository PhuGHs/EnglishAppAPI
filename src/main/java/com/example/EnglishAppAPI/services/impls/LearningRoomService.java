package com.example.EnglishAppAPI.services.impls;

import com.example.EnglishAppAPI.entities.EnglishTopic;
import com.example.EnglishAppAPI.entities.LearningRoom;
import com.example.EnglishAppAPI.entities.Participant;
import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.mapstruct.dtos.LearningRoomPostInstantDto;
import com.example.EnglishAppAPI.mapstruct.dtos.LearningRoomPostLaterDto;
import com.example.EnglishAppAPI.mapstruct.mappers.LearningRoomMapper;
import com.example.EnglishAppAPI.mapstruct.mappers.ParticipantMapper;
import com.example.EnglishAppAPI.repositories.EnglishTopicRepository;
import com.example.EnglishAppAPI.repositories.LearningRoomRepository;
import com.example.EnglishAppAPI.repositories.ParticipantRepository;
import com.example.EnglishAppAPI.repositories.UserRepository;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.responses.ApiResponseStatus;
import com.example.EnglishAppAPI.services.interfaces.ILearningRoomService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LearningRoomService implements ILearningRoomService {
    private final ParticipantRepository participantRepository;
    private final LearningRoomRepository learningRoomRepository;
    private final UserRepository userRepository;
    private final EnglishTopicRepository englishTopicRepository;
    private final LearningRoomMapper learningRoomMapper;
    private final ParticipantMapper participantMapper;

    @Autowired
    public LearningRoomService(ParticipantRepository participantRepository, LearningRoomRepository learningRoomRepository, UserRepository userRepository, EnglishTopicRepository englishTopicRepository, LearningRoomMapper learningRoomMapper, ParticipantMapper participantMapper) {
        this.participantRepository = participantRepository;
        this.learningRoomRepository = learningRoomRepository;
        this.userRepository = userRepository;
        this.englishTopicRepository = englishTopicRepository;
        this.learningRoomMapper = learningRoomMapper;
        this.participantMapper = participantMapper;
    }

    @Override
    public ResponseEntity<?> createLearningRoomInstantly(LearningRoomPostInstantDto learningRoomPostDto) {
        UserEntity owner = userRepository.findById(learningRoomPostDto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        EnglishTopic topic = englishTopicRepository.findById(learningRoomPostDto.getEnglishTopicId())
                .orElseThrow(() -> new NotFoundException("Topic not found"));

        LearningRoom learningRoom = LearningRoom.builder()
                .createdAt(LocalDateTime.now())
                .scheduledTo(null)
                .roomName(learningRoomPostDto.getRoomName())
                .maxParticipants(learningRoomPostDto.getMaxParticipants())
                .topic(topic)
                .build();

        Participant ownerParticipant = Participant.builder()
                .room(learningRoom)
                .user(owner)
                .joinTime(LocalDateTime.now())
                .isOwner(true)
                .isSpeaker(true)
                .build();
        Set<Participant> ps = new HashSet<>();
        ps.add(ownerParticipant);
        learningRoom.setParticipants(ps);
        learningRoom = learningRoomRepository.save(learningRoom);

        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "created learning room instantly", learningRoomMapper.toDto(learningRoom)));
    }

    @Override
    public ResponseEntity<?> scheduleLearningRoom(LearningRoomPostLaterDto learningRoomPostLaterDto) {
        UserEntity owner = userRepository.findById(learningRoomPostLaterDto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        EnglishTopic topic = englishTopicRepository.findById(learningRoomPostLaterDto.getEnglishTopicId())
                .orElseThrow(() -> new NotFoundException("Topic not found"));

        LearningRoom learningRoom = LearningRoom.builder()
                .createdAt(LocalDateTime.now())
                .scheduledTo(learningRoomPostLaterDto.getScheduledTo())
                .roomName(learningRoomPostLaterDto.getRoomName())
                .maxParticipants(learningRoomPostLaterDto.getMaxParticipants())
                .topic(topic)
                .build();

        Participant ownerParticipant = Participant.builder()
                .room(learningRoom)
                .user(owner)
                .joinTime(LocalDateTime.now())
                .isOwner(true)
                .isSpeaker(true)
                .build();
        Set<Participant> ps = new HashSet<>();
        ps.add(ownerParticipant);
        learningRoom.setParticipants(ps);
        learningRoom = learningRoomRepository.save(learningRoom);

        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "created learning room later", learningRoomMapper.toDto(learningRoom)));
    }

    @Override
    public ResponseEntity<?> joinLearningRoom(Long roomId, Long userId) {
        LearningRoom learningRoom = learningRoomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("learning room not found"));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user not found"));
        Participant participant = Participant.builder()
                .joinTime(LocalDateTime.now())
                .room(learningRoom)
                .user(user)
                .isSpeaker(false)
                .isOwner(false)
                .build();
        learningRoom.getParticipants().add(participant);
        learningRoom = learningRoomRepository.save(learningRoom);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "joined the room", learningRoomMapper.toDto(learningRoom)));
    }

    @Override
    public ResponseEntity<?> kickParticipant(Long participantId) {
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new NotFoundException("participant not found"));
        //send to client
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
        participantRepository.save(participant);
        participantRepository.save(owner);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "promoted the participant to owner", ""));
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
    public ResponseEntity<?> getLearningRooms(@Nullable LocalDateTime startDate, @Nullable LocalDateTime endDate, boolean isLive) {
        List<LearningRoom> learningRooms = null;
        if (isLive) {
            learningRooms = learningRoomRepository.getLiveLearningRoom();
            return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get live room", learningRooms.stream().map(learningRoomMapper::toDto)));
        }
        learningRooms = learningRoomRepository.getLearningRoomBetween(startDate, endDate);
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
}
