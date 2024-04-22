package com.example.EnglishAppAPI.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ConfigConstants {
    @Value("${jwt.secretKey}")
    private String JwtSecretKey;
    @Value("${jwt.expiration}")
    private String JwtExpiration;
}
