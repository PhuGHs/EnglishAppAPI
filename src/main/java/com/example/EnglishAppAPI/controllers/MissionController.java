package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.models.ApiResponse;
import com.example.EnglishAppAPI.services.DiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/missions")
public class MissionController {
}
