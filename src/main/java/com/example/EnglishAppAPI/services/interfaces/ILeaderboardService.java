package com.example.EnglishAppAPI.services.interfaces;

import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
public interface ILeaderboardService {
    ResponseEntity<?> getLeaderboardByDateRange(String key, int startRank, int endRank);
    void updateScore(String leaderboardKey, Long userId, int pointsToAdd);
}
