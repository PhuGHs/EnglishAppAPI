package com.example.EnglishAppAPI.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/missions")
@SecurityRequirement(name = "bearerAuth")
public class MissionController {
}
