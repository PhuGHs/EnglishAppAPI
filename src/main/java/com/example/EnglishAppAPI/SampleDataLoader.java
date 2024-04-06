package com.example.EnglishAppAPI;

import com.example.EnglishAppAPI.entities.*;
import com.example.EnglishAppAPI.repositories.*;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class SampleDataLoader implements CommandLineRunner {
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
    private final Faker faker;

    @Autowired
    public SampleDataLoader(RoleRepository roleRepository, EnglishLevelRepository englishLevelRepository, AccountRepository accountRepository, EnglishTestRepository englishTestRepository, EnglishTopicQuestionRepository englishTopicQuestionRepository, EnglishTopicRepository englishTopicRepository, InterestRepository interestRepository, LearningRoomRepository learningRoomRepository, MissionRepository missionRepository, NotificationRepository notificationRepository, ParticipantRepository participantRepository, QuestionRepository questionRepository, ReportRepository reportRepository, UserMissionRepository userMissionRepository, UserRepository userRepository) {
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
        this.faker = new Faker();
    }

    public void generateSampleData() {
        // Save roles
        Role learner = new Role("LEARNER");
        Role administrator = new Role("ADMIN");
        roleRepository.save(learner);
        roleRepository.save(administrator);

        // Save English levels and topics
        List<EnglishLevel> englishLevels = Arrays.asList(
                new EnglishLevel("A1 Elementary", "Description"),
                new EnglishLevel("A2 Pre Intermediate", "Description"),
                new EnglishLevel("B1 Intermediate", "Description"),
                new EnglishLevel("B2 Upper Intermediate", "Description"),
                new EnglishLevel("C1 Advanced", "Description"),
                new EnglishLevel("C2 Proficient", "Description")
        );
        englishLevelRepository.saveAll(englishLevels);

        // Generate English topics for each level
        englishLevels.forEach(level -> {
            List<EnglishTopic> englishTopics = IntStream.rangeClosed(1,5)
                    .mapToObj(i -> {
                        EnglishTopic topic = new EnglishTopic(faker.lorem().sentence(5), faker.lorem().sentence(5));
                        topic.setEnglishLevel(level);
                        return topic;
                    }).toList();
            englishTopicRepository.saveAll(englishTopics);
            englishTopics.forEach(topic -> {
                List<EnglishTopicQuestion> topicQuestions = IntStream.rangeClosed(1, 10)
                        .mapToObj(i -> {
                            EnglishTopicQuestion topicQuestion = new EnglishTopicQuestion(faker.lorem().sentence(5), faker.lorem().sentence(5));
                            topicQuestion.setTopic(topic);
                            return topicQuestion;
                        }).toList();
                englishTopicQuestionRepository.saveAll(topicQuestions);
            });
        });

        // Save predefined interests
        List<Interest> predefinedInterests = Arrays.asList(
                new Interest("Music"),
                new Interest("Sports"),
                new Interest("Reading"),
                new Interest("Cooking"),
                new Interest("Traveling")
        );
        interestRepository.saveAll(predefinedInterests);

        // Save missions
        List<Mission> missions = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> new Mission("Mission" + i, faker.random().nextInt(50, 200), faker.date().birthday())).toList();
        missionRepository.saveAll(missions);

        // Generate users
        List<UserEntity> users = IntStream.rangeClosed(1, 50)
                .mapToObj(i -> {
                    UserEntity user = new UserEntity(
                            faker.name().fullName(),
                            faker.bool().bool()
                    );
                    user.setInterests(new HashSet<>(predefinedInterests));
                    return user;
                })
                .collect(Collectors.toList());
        userRepository.saveAll(users);

        // Generate user missions
        users.forEach(user -> {
            List<Mission> shuffledMissions = new ArrayList<>(missions);
            Collections.shuffle(shuffledMissions);

            List<Mission> randomMissions = shuffledMissions.subList(0, Math.min(shuffledMissions.size(), 4));
            randomMissions.forEach(mission -> {
                UserMission userMission = new UserMission();
                userMission.setUser(user);
                userMission.setMission(mission);
                userMission.setCompleted(faker.bool().bool());
                userMissionRepository.save(userMission);
            });
        });

        // Save accounts
        List<Account> accounts = users.stream()
                .map(user -> {
                    Account account = new Account(
                            faker.internet().emailAddress(),
                            faker.internet().password()
                    );
                    account.setUser(user);
                    account.setRole(learner);

                    EnglishLevel randomLevel = englishLevels.get(faker.random().nextInt(englishLevels.size()));
                    user.setEnglishLevel(randomLevel);
                    return account;
                })
                .collect(Collectors.toList());
        accountRepository.saveAll(accounts);
    }


    @Override
    public void run(String... args) throws Exception {
//        generateSampleData();
    }
}
