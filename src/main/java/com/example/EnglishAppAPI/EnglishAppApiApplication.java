package com.example.EnglishAppAPI;

import com.example.EnglishAppAPI.configuration.ConfigConstants;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "EnglishAppAPI", version = "1.0", description = "This is an english app that helps people to learn English"))
@SecurityScheme(name = "bearerAuth", scheme = "bearer", bearerFormat = "JWT", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
@EnableScheduling
public class EnglishAppApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(EnglishAppApiApplication.class, args);
	}
}
