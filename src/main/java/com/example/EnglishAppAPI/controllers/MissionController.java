package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.mapstruct.dtos.UserMissionPostDto;
import com.example.EnglishAppAPI.services.impls.MissionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/missions")
@RestControllerAdvice
@Validated
@SecurityRequirement(name = "bearerAuth")
public class MissionController {
    @Autowired
    private MissionService missionService;
    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> getUserMissions(@Valid @PathVariable @NotNull(message = "this userId is required") Long userId) {
        return missionService.getUserMissions(userId);
    }
    @PutMapping("/do-mission")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<?> doMissions(@Valid @RequestBody UserMissionPostDto userMissionPostDto) {
        return missionService.updateMission(userMissionPostDto);
    }
    @PutMapping("/refresh")
    public ResponseEntity<?> refreshMissions() {
        return missionService.refreshMissions();
    }

    @PostMapping("/{userId}/add-missions")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addMissions(@Valid @NotNull(message = "the userId is required") @PathVariable Long userId) {
        return missionService.addMissions(userId);
    }

    @GetMapping("/{userId}/get-percentage")
    public ResponseEntity<?> getMissionPercentage(@Valid @NotNull(message = "the userId is required") @PathVariable Long userId) {
        return missionService.getMissionPercentage(userId);
    }
}
