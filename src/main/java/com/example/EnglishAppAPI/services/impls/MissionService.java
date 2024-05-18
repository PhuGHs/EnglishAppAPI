package com.example.EnglishAppAPI.services.impls;

import com.example.EnglishAppAPI.entities.Mission;
import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.entities.UserMission;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.mapstruct.dtos.UserMissionPostDto;
import com.example.EnglishAppAPI.mapstruct.enums.LeaderboardTimePeriod;
import com.example.EnglishAppAPI.mapstruct.mappers.UserMissionMapper;
import com.example.EnglishAppAPI.repositories.MissionRepository;
import com.example.EnglishAppAPI.repositories.UserMissionRepository;
import com.example.EnglishAppAPI.repositories.UserRepository;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.responses.ApiResponseStatus;
import com.example.EnglishAppAPI.services.interfaces.IMissionService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MissionService implements IMissionService {
    @Autowired
    public MissionService(MissionRepository missionRepository, UserMissionRepository userMissionRepository, UserRepository userRepository, LeaderboardService leaderboardService, UserMissionMapper userMissionMapper) {
        this.missionRepository = missionRepository;
        this.userMissionRepository = userMissionRepository;
        this.userRepository = userRepository;
        this.leaderboardService = leaderboardService;
        this.userMissionMapper = userMissionMapper;
    }
    private final MissionRepository missionRepository;
    private final UserMissionRepository userMissionRepository;
    private final UserRepository userRepository;
    private final LeaderboardService leaderboardService;
    private final UserMissionMapper userMissionMapper;
    @Override
    public ResponseEntity<?> getUserMissions(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user is not found"));
        List<UserMission> userMissions = userMissionRepository.findByUser(user);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get user missions", userMissions.stream().map(userMissionMapper::toDto)));
    }

    @Override
    public ResponseEntity<?> updateMission(UserMissionPostDto userMissionDto) {
        UserEntity user = userRepository.findById(userMissionDto.getUserId())
                .orElseThrow(() -> new NotFoundException("user is not found"));
        Mission mission = missionRepository.findById(userMissionDto.getMissionId())
                .orElseThrow(() -> new NotFoundException("mission is not found"));
        UserMission userMission = userMissionRepository.findByUserAndMission(user, mission);
        if (userMission.isCompleted()) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("this mission was done");
        } else {
            userMission.setCompletionCount(userMission.getCompletionCount() + 1);
            String leaderBoardWeekKey = LeaderboardService.generateLeaderboardKey(LeaderboardTimePeriod.week);
            String leaderBoardMonthKey = LeaderboardService.generateLeaderboardKey(LeaderboardTimePeriod.month);
            String leaderBoardYearKey = LeaderboardService.generateLeaderboardKey(LeaderboardTimePeriod.year);
            leaderboardService.updateScore(leaderBoardWeekKey, user.getUserId(), mission.getPointsAwarded());
            leaderboardService.updateScore(leaderBoardMonthKey, user.getUserId(), mission.getPointsAwarded());
            leaderboardService.updateScore(leaderBoardYearKey, user.getUserId(), mission.getPointsAwarded());
            if (userMission.getCompletionCount() == mission.getMaxCompletionCount()) {
                userMission.setCompleted(true);
            }
            userMissionRepository.save(userMission);
        }
        return ResponseEntity.ok("updated successfully");
    }

    @Override
    public ResponseEntity<?> refreshMissions() {
        List<UserMission> userMissions = userMissionRepository.findAll();
        for (UserMission mission : userMissions) {
            mission.setCompletionCount(0);
            mission.setCompleted(false);
        }
        userMissionRepository.saveAll(userMissions);
        return ResponseEntity.ok("refreshed missions");
    }

    @Scheduled(cron = "0 0 0 * * *") //execute the task every day at 0 AM
    public void refresh() {
        List<UserMission> userMissions = userMissionRepository.findAll();
        for (UserMission mission : userMissions) {
            mission.setCompletionCount(0);
            mission.setCompleted(false);
        }
        userMissionRepository.saveAll(userMissions);
    }

    @Override
    public ResponseEntity<?> addMissions(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user is not found"));
        Mission mission = new Mission("Read a blog or a short story", 5, 1);
        Mission mission1 = new Mission("Ask a question", 10, 1);
        Mission mission2 = new Mission("Join a learning room", 10, 5);
        Mission mission3 = new Mission("Host a learning room", 10, 3);
        if (!missionRepository.existsByMissionName(mission.getMissionName())
            && !missionRepository.existsByMissionName(mission1.getMissionName())
            && !missionRepository.existsByMissionName(mission2.getMissionName())
            && !missionRepository.existsByMissionName(mission3.getMissionName())
        ) {
            missionRepository.save(mission);
            missionRepository.save(mission1);
            missionRepository.save(mission2);
            missionRepository.save(mission3);
        }
        UserMission userMission = UserMission.builder()
                .user(user)
                .mission(mission)
                .build();
        UserMission userMission1 = UserMission.builder()
                .user(user)
                .mission(mission1)
                .build();
        UserMission userMission2 = UserMission.builder()
                .user(user)
                .mission(mission2)
                .build();
        UserMission userMission3 = UserMission.builder()
                .user(user)
                .mission(mission3)
                .build();
        userMissionRepository.save(userMission);
        userMissionRepository.save(userMission1);
        userMissionRepository.save(userMission2);
        userMissionRepository.save(userMission3);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "add user missions", user.getMissions()));
    }

    @Override
    public ResponseEntity<?> getMissionPercentage(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user is not found"));
        List<UserMission> userMissions = userMissionRepository.findByUser(user);
        int count = 0;
        if (userMissions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ApiResponse(ApiResponseStatus.FAIL, "get mission percentage", "there is no missions"));
        }
        for (UserMission mission : userMissions) {
            if (mission.isCompleted()) count = count + 1;
        }
        int percentage = count * 100 / userMissions.size();
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get mission percentage", percentage));
    }
}
