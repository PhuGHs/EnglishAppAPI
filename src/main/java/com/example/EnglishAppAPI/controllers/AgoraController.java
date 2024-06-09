package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.mapstruct.dtos.AgoraBody;
import io.agora.media.RtcTokenBuilder;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RestControllerAdvice
@RequestMapping("${api.prefix}/agora")
@SecurityRequirement(name = "bearerAuth")
public class AgoraController {
    @Value("${AGORA_APP_ID}")
    private String appId;
    @Value("${AGORA_APP_CERTIFICATE}")
    private String appCertificate;
    @Value("${AGORA_TOKEN_EXPIRATION_TIME}")
    private int tokenExpirationInSeconds;
    @Value("${AGORA_PRIVILEGE_EXPIRATION_TIME}")
    private int privilegeExpirationInSeconds;

    @PostMapping("/generate-token")
    public String generateToken(@RequestBody AgoraBody body) {
        if (appId == null || appId.isEmpty() || appCertificate == null || appCertificate.isEmpty()) {
            System.out.printf("Need to set environment variable AGORA_APP_ID and AGORA_APP_CERTIFICATE\n");
            return "";
        }

        RtcTokenBuilder token = new RtcTokenBuilder();
        String result = token.buildTokenWithUid(appId, appCertificate, body.getChannelName(), body.getUid(), RtcTokenBuilder.Role.Role_Subscriber, privilegeExpirationInSeconds);
        return result;
    }
}