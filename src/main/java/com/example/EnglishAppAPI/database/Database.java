package com.example.EnglishAppAPI.database;

import com.example.EnglishAppAPI.entities.*;
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

                EnglishLevel A1 = new EnglishLevel("A1 Elementary", "If a person is at A0, this is a beginner with the ability to say and understand just a few words or phrases, or he/she may have no knowledge of English at all.");
                EnglishLevel A2 = new EnglishLevel("A2 Pre Intermediate", "If a person is at A0, this is a beginner with the ability to say and understand just a few words or phrases, or he/she may have no knowledge of English at all.");
                EnglishLevel B1 = new EnglishLevel("B1 Intermediate", "If a person is at A0, this is a beginner with the ability to say and understand just a few words or phrases, or he/she may have no knowledge of English at all.");
                EnglishLevel B2 = new EnglishLevel("B2 Upper Intermediate", "If a person is at A0, this is a beginner with the ability to say and understand just a few words or phrases, or he/she may have no knowledge of English at all.");
                EnglishLevel C1 = new EnglishLevel("C1 Advanced", "If a person is at A0, this is a beginner with the ability to say and understand just a few words or phrases, or he/she may have no knowledge of English at all.");
                EnglishLevel C2 = new EnglishLevel("C2 Proficient", "If a person is at A0, this is a beginner with the ability to say and understand just a few words or phrases, or he/she may have no knowledge of English at all.");

                UserEntity user1 = new UserEntity("John Doe", true);
                user1.setEnglishLevel(A2);
                UserEntity user2 = new UserEntity("John Doe", true);
                user2.setEnglishLevel(C1);
                UserEntity user3 = new UserEntity("John Doe", true);
                user3.setEnglishLevel(B1);
                UserEntity user4 = new UserEntity("John Doe", true);
                user4.setEnglishLevel(B1);

                Account account1 = new Account("levanphu@gmail.com", "123456");
                account1.setUser(user1);
                Account account2 = new Account("levanphu1@gmail.com", "123456");
                account2.setUser(user2);
                Account account3 = new Account("levanphu2@gmail.com", "123456");
                account3.setUser(user3);
                Account account4 = new Account("levanphu3@gmail.com", "123456");
                account4.setUser(user4);
            }
        };
    }
}
