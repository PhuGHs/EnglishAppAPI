package com.example.EnglishAppAPI.database;

import com.example.EnglishAppAPI.entities.*;
import com.example.EnglishAppAPI.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

@Configuration
public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    private final RoleRepository roleRepository;
    private final EnglishLevelRepository englishLevelRepository;
    private final AccountRepository accountRepository;
    private final EnglishTestRepository englishTestRepository;
    private final EnglishTopicQuestionRepository englishTopicQuestionRepository;
    private final EnglishTopicRepository englishTopicRepository;
    private final InterestRepository interestRepository;
    private final LearningRoomRepository learningRoomRepository;
    private final MissionRepository missionRepository;
    private final NotificationRepository notificationRepository;
    private final ParticipantRepository participantRepository;
    private final QuestionRepository questionRepository;
    private final ReportRepository reportRepository;
    private final UserMissionRepository userMissionRepository;
    private final UserRepository userRepository;

    @Autowired
    public Database(RoleRepository roleRepository, EnglishLevelRepository englishLevelRepository, AccountRepository accountRepository, EnglishTestRepository englishTestRepository, EnglishTopicQuestionRepository englishTopicQuestionRepository, EnglishTopicRepository englishTopicRepository, InterestRepository interestRepository, LearningRoomRepository learningRoomRepository, MissionRepository missionRepository, NotificationRepository notificationRepository, ParticipantRepository participantRepository, QuestionRepository questionRepository, ReportRepository reportRepository, UserMissionRepository userMissionRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.englishLevelRepository = englishLevelRepository;
        this.accountRepository = accountRepository;
        this.englishTestRepository = englishTestRepository;
        this.englishTopicQuestionRepository = englishTopicQuestionRepository;
        this.englishTopicRepository = englishTopicRepository;
        this.interestRepository = interestRepository;
        this.learningRoomRepository = learningRoomRepository;
        this.missionRepository = missionRepository;
        this.notificationRepository = notificationRepository;
        this.participantRepository = participantRepository;
        this.questionRepository = questionRepository;
        this.reportRepository = reportRepository;
        this.userMissionRepository = userMissionRepository;
        this.userRepository = userRepository;
    }

    @Bean
    CommandLineRunner initDatabase() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
//                if (!roleRepository.existsByRoleName("USER")) {
//                    Role userRole = new Role("USER");
//                    roleRepository.save(userRole);
//                }
//                if (!roleRepository.existsByRoleName("ADMIN")) {
//                    Role adminRole = new Role("ADMIN");
//                    roleRepository.save(adminRole);
//                }
//
//                EnglishLevel A1 = new EnglishLevel("A1 Elementary", "If a person is at A0, this is a beginner with the ability to say and understand just a few words or phrases, or he/she may have no knowledge of English at all.");
//                EnglishLevel A2 = new EnglishLevel("A2 Pre Intermediate", "If a person is at A0, this is a beginner with the ability to say and understand just a few words or phrases, or he/she may have no knowledge of English at all.");
//                EnglishLevel B1 = new EnglishLevel("B1 Intermediate", "If a person is at A0, this is a beginner with the ability to say and understand just a few words or phrases, or he/she may have no knowledge of English at all.");
//                EnglishLevel B2 = new EnglishLevel("B2 Upper Intermediate", "If a person is at A0, this is a beginner with the ability to say and understand just a few words or phrases, or he/she may have no knowledge of English at all.");
//                EnglishLevel C1 = new EnglishLevel("C1 Advanced", "If a person is at A0, this is a beginner with the ability to say and understand just a few words or phrases, or he/she may have no knowledge of English at all.");
//                EnglishLevel C2 = new EnglishLevel("C2 Proficient", "If a person is at A0, this is a beginner with the ability to say and understand just a few words or phrases, or he/she may have no knowledge of English at all.");
//                englishLevelRepository.save(A1);
//                englishLevelRepository.save(A2);
//                englishLevelRepository.save(B1);
//                englishLevelRepository.save(B2);
//                englishLevelRepository.save(C1);
//                englishLevelRepository.save(C2);
//
//                UserEntity user1 = new UserEntity("John Doe", true);
//                user1.setEnglishLevel(A2);
//                UserEntity user2 = new UserEntity("John Doe", true);
//                user2.setEnglishLevel(C1);
//                UserEntity user3 = new UserEntity("John Doe", true);
//                user3.setEnglishLevel(B1);
//                UserEntity user4 = new UserEntity("John Doe", true);
//                user4.setEnglishLevel(B1);
//                userRepository.save(user1);
//                userRepository.save(user2);
//                userRepository.save(user3);
//                userRepository.save(user4);
//
//                Account account1 = new Account("levanphu@gmail.com", "123456");
//                account1.setUser(user1);
//                Account account2 = new Account("levanphu1@gmail.com", "123456");
//                account2.setUser(user2);
//                Account account3 = new Account("levanphu2@gmail.com", "123456");
//                account3.setUser(user3);
//                Account account4 = new Account("levanphu3@gmail.com", "123456");
//                account4.setUser(user4);
//                accountRepository.save(account1);
//                accountRepository.save(account2);
//                accountRepository.save(account3);
//                accountRepository.save(account4);
            }
        };
    }
}
