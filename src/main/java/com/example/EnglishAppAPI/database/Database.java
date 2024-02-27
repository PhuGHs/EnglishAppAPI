package com.example.EnglishAppAPI.database;

import com.example.EnglishAppAPI.entities.Role;
import com.example.EnglishAppAPI.repositories.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    private final RoleRepository roleRepository;
    @Autowired
    public Database(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Bean
    CommandLineRunner initDatabase() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                if (!roleRepository.existsByRoleName("USER")) {
                    Role userRole = new Role("USER");
                    roleRepository.save(userRole);
                }
                if (!roleRepository.existsByRoleName("ADMIN")) {
                    Role adminRole = new Role("ADMIN");
                    roleRepository.save(adminRole);
                }
            }
        };
    }
}
