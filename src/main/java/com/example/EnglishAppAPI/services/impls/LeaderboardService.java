package com.example.EnglishAppAPI.services.impls;

import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.exceptions.NullVariableException;
import com.example.EnglishAppAPI.mapstruct.dtos.LeaderboardDto;
import com.example.EnglishAppAPI.mapstruct.enums.LeaderboardTimePeriod;
import com.example.EnglishAppAPI.mapstruct.mappers.UserMapper;
import com.example.EnglishAppAPI.repositories.UserRepository;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.responses.ApiResponseStatus;
import com.example.EnglishAppAPI.services.interfaces.ILeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class LeaderboardService implements ILeaderboardService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;
    @Override
    public ResponseEntity<?> getLeaderboardByDateRange(String key, int startRank, int endRank) {
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> result = zSetOperations.rangeWithScores(key, startRank, endRank);
        assert result != null;
        List<LeaderboardDto> leaderboardDtos = result.stream()
                .map(this::mapToLeaderboardDto)
                .toList();
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get leaderboard", leaderboardDtos));
    }

    @Override
    public void updateScore(String leaderboardKey, Long userId, int pointsToAdd) {
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.incrementScore(leaderboardKey, userId.toString(), Double.parseDouble(String.valueOf(pointsToAdd)));
    }

    public static String generateLeaderboardKey(LeaderboardTimePeriod timePeriod) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = null;

        switch (timePeriod) {
            case week -> formatter = DateTimeFormatter.ofPattern("yyyy-MM-'W'ww");
            case year -> formatter = DateTimeFormatter.ofPattern("yyyy");
            case month -> formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        }
        return "leaderboard:" + timePeriod + ":" + currentDate.format(formatter);
    }

    private LeaderboardDto mapToLeaderboardDto(ZSetOperations.TypedTuple<String> tuple) {
        LeaderboardDto leaderboardDto = new LeaderboardDto();
        UserEntity user = userRepository.findById(Long.parseLong(Objects.requireNonNull(tuple.getValue())))
                        .orElseThrow(() -> new NotFoundException("user is not found"));
        leaderboardDto.setUser(userMapper.toNecessaryDto(user));
        if (tuple.getScore() != null) {
            leaderboardDto.setScore(tuple.getScore());
        } else {
            throw new NullVariableException("the score is null");
        }
        return leaderboardDto;
    }
}
