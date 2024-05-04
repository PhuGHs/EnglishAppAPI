package com.example.EnglishAppAPI.services.impls;

import com.example.EnglishAppAPI.entities.EnglishTopic;
import com.example.EnglishAppAPI.entities.LearningRoom;
import com.example.EnglishAppAPI.entities.Participant;
import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.mapstruct.dtos.LearningRoomPostInstantDto;
import com.example.EnglishAppAPI.mapstruct.mappers.LearningRoomMapper;
import com.example.EnglishAppAPI.repositories.EnglishTopicRepository;
import com.example.EnglishAppAPI.repositories.LearningRoomRepository;
import com.example.EnglishAppAPI.repositories.ParticipantRepository;
import com.example.EnglishAppAPI.repositories.UserRepository;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.responses.ApiResponseStatus;
import com.example.EnglishAppAPI.services.interfaces.ILearningRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class LearningRoomService implements ILearningRoomService {
    private final ParticipantRepository participantRepository;
    private final LearningRoomRepository learningRoomRepository;
    private final UserRepository userRepository;
    private final EnglishTopicRepository englishTopicRepository;
    private final LearningRoomMapper learningRoomMapper;

    @Autowired
    public LearningRoomService(ParticipantRepository participantRepository, LearningRoomRepository learningRoomRepository, UserRepository userRepository, EnglishTopicRepository englishTopicRepository, LearningRoomMapper learningRoomMapper) {
        this.participantRepository = participantRepository;
        this.learningRoomRepository = learningRoomRepository;
        this.userRepository = userRepository;
        this.englishTopicRepository = englishTopicRepository;
        this.learningRoomMapper = learningRoomMapper;
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
    public ResponseEntity<?> scheduleLearningRoom() {
        return null;
    }

    @Override
    public ResponseEntity<?> joinLearningRoom() {
        return null;
    }

    @Override
    public ResponseEntity<?> kickParticipant() {
        return null;
    }

    @Override
    public ResponseEntity<?> promoteParticipantToOwner() {
        return null;
    }

    @Override
    public ResponseEntity<?> getParticipants() {
        return null;
    }
}
