package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.mapstruct.enums.LeaderboardTimePeriod;
import com.example.EnglishAppAPI.services.impls.LeaderboardService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@RestController
@RequestMapping("${api.prefix}/leaderboard")
@SecurityRequirement(name = "bearerAuth")
public class LeaderboardController {
    @Autowired
    private LeaderboardService leaderboardService;
    @GetMapping("/week")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> getLeaderboardByWeek(@RequestParam int startRank, @RequestParam int endRank) {
        return leaderboardService.getLeaderboardByDateRange(LeaderboardService.generateLeaderboardKey(LeaderboardTimePeriod.week), startRank, endRank);
    }

    @GetMapping("/month")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> getLeaderboardByMonth(@RequestParam int startRank, @RequestParam int endRank) {
        return leaderboardService.getLeaderboardByDateRange(LeaderboardService.generateLeaderboardKey(LeaderboardTimePeriod.month), startRank, endRank);
    }

    @GetMapping("/year")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> getLeaderboardByYear(@RequestParam int startRank, @RequestParam int endRank) {
        return leaderboardService.getLeaderboardByDateRange(LeaderboardService.generateLeaderboardKey(LeaderboardTimePeriod.year), startRank, endRank);
    }
}
