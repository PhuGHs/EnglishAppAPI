package com.example.EnglishAppAPI;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "EnglishAppAPI", version = "1.0", description = "This is an english app that helps people to learn English"))
@SecurityScheme(name = "authorize", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class EnglishAppApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(EnglishAppApiApplication.class, args);
	}
}
