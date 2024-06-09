package com.example.EnglishAppAPI.services.impls;

import com.example.EnglishAppAPI.entities.*;
import com.example.EnglishAppAPI.exceptions.ErrorResponse;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTestPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.QuestionPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.SubmitTestDto;
import com.example.EnglishAppAPI.mapstruct.mappers.EnglishTestMapper;
import com.example.EnglishAppAPI.mapstruct.mappers.QuestionMapper;
import com.example.EnglishAppAPI.mapstruct.mappers.UserMapper;
import com.example.EnglishAppAPI.repositories.*;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.responses.ApiResponseStatus;
import com.example.EnglishAppAPI.services.interfaces.IEnglishTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnglishTestService implements IEnglishTestService {
    private final EnglishTestRepository englishTestRepository;
    private final EnglishTestMapper englishTestMapper;
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final EnglishLevelRepository englishLevelRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserService userService;
    private final UserTestRepository userTestRepository;

    @Autowired
    public EnglishTestService(EnglishTestRepository englishTestRepository, EnglishTestMapper englishTestMapper, QuestionRepository questionRepository, QuestionMapper questionMapper, EnglishLevelRepository englishLevelRepository, UserRepository userRepository, UserMapper userMapper, UserService userService, UserTestRepository userTestRepository) {
        this.englishTestRepository = englishTestRepository;
        this.englishTestMapper = englishTestMapper;
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
        this.englishLevelRepository = englishLevelRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userService = userService;
        this.userTestRepository = userTestRepository;
    }

    @Override
    public ResponseEntity<?> createEnglishTest(EnglishTestPostDto englishTestPostDto) {
        EnglishLevel level = englishLevelRepository.findById(englishTestPostDto.getEnglishLevelId())
                .orElseThrow(() -> new NotFoundException("English level not found"));
        EnglishTest englishTest = EnglishTest.builder()
                .description(englishTestPostDto.getDescription())
                .title(englishTestPostDto.getTitle())
                .englishLevel(level)
                .numberOfQuestions(englishTestPostDto.getNumberOfQuestions())
                .build();
        englishTest = englishTestRepository.save(englishTest);

        //add to users
        List<UserTest> userTests = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findAll();
        for (UserEntity userEntity : userEntities) {
            UserTest userTest = UserTest.builder()
                    .test(englishTest)
                    .user(userEntity)
                    .score(0)
                    .isPassed(false)
                    .build();
            userTests.add(userTest);
        }
        userTestRepository.saveAll(userTests);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "create english test", englishTestMapper.toEnglishTestDto(englishTest)));
    }

    @Override
    public ResponseEntity<?> modifyEnglishTest(EnglishTestPostDto englishTestPostDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteEnglishTest(Long id) {
        if (!englishTestRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        englishTestRepository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "delete english test", ""));
    }

    @Override
    public ResponseEntity<?> getEnglishTests(Long id) {
        List<EnglishTest> englishTests = englishTestRepository.getEnglishTest(id);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get english tests", englishTests.stream().map(englishTestMapper::toEnglishTestDto)));
    }

    @Override
    public ResponseEntity<?> getQuestions(Long testId) {
        EnglishTest englishTest = englishTestRepository.findById(testId)
                .orElseThrow(() -> new NotFoundException("english test not found"));
        List<Question> questions = questionRepository.findQuestionsByEnglishTest(englishTest);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get questions", questions.stream().map(questionMapper::toDto)));
    }

    @Override
    public ResponseEntity<?> submitTest(SubmitTestDto submitTestDto) {
        EnglishTest englishTest = englishTestRepository.findById(submitTestDto.getId())
                .orElseThrow(() -> new NotFoundException("english test not found"));
        UserEntity user = userRepository.findById(submitTestDto.getUserId())
                .orElseThrow(() -> new NotFoundException("user not found"));

        int numberOfQuestions = englishTest.getNumberOfQuestions();
        Long testId = englishTest.getEnglishTestId();
        Long userId = user.getUserId();

        if (!submitTestDto.isEntryLevelTest()) {
            if (submitTestDto.getScore() >= numberOfQuestions / 2) {
                EnglishLevel level = englishLevelRepository.findById(user.getEnglishLevel().getLevelId() + 1)
                        .orElseThrow(() -> new NotFoundException("English level not found"));
                UserTest userTest = userTestRepository.getUserTestsByTestAndUser(userId, testId);
                userTest.setScore(submitTestDto.getScore());
                userTest.setPassed(true);
                userTest = userTestRepository.save(userTest);

                user.setEnglishLevel(level);
                user = userRepository.save(user);
                userService.updateUser(user);
                return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "submit tests", userMapper.toNecDto(user)));
            }
            return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "failed", ""));
        } else {
            Long level;
            int score = submitTestDto.getScore();
            if (score >= 0 && score <= 5) {
                level = 1L;
            } else if (score >= 6 && score <= 10) {
                level = 2L;
            } else if (score >= 11 && score <= 16) {
                level = 3L;
            } else if (score >= 17 && score <= 20) {
                level = 4L;
            } else {
                return ResponseEntity.badRequest().body("Invalid score");
            }
            EnglishLevel englishLevel = englishLevelRepository.findById(level)
                    .orElseThrow(() -> new NotFoundException("cant find the english level"));
            user.setEnglishLevel(englishLevel);
            user = userRepository.save(user);
            userService.updateUser(user);
//            List<UserTest> userTests = userTestRepository.getUserTests(userId,);
            for (Long i = 1L; i <= level; i++) {
                List<UserTest> userTests = userTestRepository.getUserTests(userId, i);
                for (UserTest userTest : userTests) {
                    userTest.setScore(userTest.getTest().getNumberOfQuestions());
                    userTest.setPassed(true);
                }
                userTestRepository.saveAll(userTests);
            }
            return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "update english level", userMapper.toNecDto(user)));
        }
    }

    @Override
    public ResponseEntity<?> insertQuestionToEnglishTest(QuestionPostDto questionPostDto) {
        EnglishTest englishTest = englishTestRepository.findById(questionPostDto.getEnglishTestId())
                .orElseThrow(() -> new NotFoundException("english test not found"));
        List<Question> questions = questionRepository.findQuestionsByEnglishTest(englishTest);
        if (questions.size() > englishTest.getNumberOfQuestions()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST, "number of questions exceed the maxium number of questions of the english test"));
        }
        Question question = questionMapper.toEntity(questionPostDto);
        question = questionRepository.save(question);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "insert question to english test", questionMapper.toDto(question)));
    }

    @Override
    public ResponseEntity<?> getUserTests(Long userId, Long levelId) {
        List<UserTest> tests = userTestRepository.getUserTests(userId, levelId);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get user tests", tests.stream().map(englishTestMapper::toUserTestDto)));
    }

    @Override
    public void insertTestsToUsers(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user not found"));
        List<EnglishTest> tests = englishTestRepository.findAll();
        List<UserTest> userTestList = new ArrayList<>();
        for (EnglishTest englishTest : tests) {
            UserTest userTest = UserTest.builder()
                    .user(user)
                    .test(englishTest)
                    .score(0)
                    .isPassed(false)
                    .build();
            userTestList.add(userTest);
        }
        userTestRepository.saveAll(userTestList);
    }
}
