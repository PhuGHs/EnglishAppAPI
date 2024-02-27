package com.example.EnglishAppAPI.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ConfigConstants {
    private Environment env;
    public static final long JWT_EXPIRATION = 70000;

    @Autowired
    public ConfigConstants(Environment env) {
        this.env = env;
    }

    public String getJWT_KEY() {
        return env.getProperty("JWT_SECRET_KEY");
    }
}
