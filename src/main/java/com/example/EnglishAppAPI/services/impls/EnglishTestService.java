package com.example.EnglishAppAPI.services.impls;

import com.example.EnglishAppAPI.entities.EnglishTest;
import com.example.EnglishAppAPI.entities.Question;
import com.example.EnglishAppAPI.exceptions.ErrorResponse;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTestPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.QuestionPostDto;
import com.example.EnglishAppAPI.mapstruct.mappers.EnglishTestMapper;
import com.example.EnglishAppAPI.mapstruct.mappers.QuestionMapper;
import com.example.EnglishAppAPI.repositories.EnglishTestRepository;
import com.example.EnglishAppAPI.repositories.QuestionRepository;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.responses.ApiResponseStatus;
import com.example.EnglishAppAPI.services.interfaces.IEnglishTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnglishTestService implements IEnglishTestService {
    private final EnglishTestRepository englishTestRepository;
    private final EnglishTestMapper englishTestMapper;
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    @Autowired
    public EnglishTestService(EnglishTestRepository englishTestRepository, EnglishTestMapper englishTestMapper, QuestionRepository questionRepository, QuestionMapper questionMapper) {
        this.englishTestRepository = englishTestRepository;
        this.englishTestMapper = englishTestMapper;
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
    }

    @Override
    public ResponseEntity<?> createEnglishTest(EnglishTestPostDto englishTestPostDto) {
        EnglishTest englishTest = EnglishTest.builder()
                .description(englishTestPostDto.getDescription())
                .title(englishTestPostDto.getTitle())
                .numberOfQuestions(englishTestPostDto.getNumberOfQuestions())
                .build();
        englishTest = englishTestRepository.save(englishTest);
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
    public ResponseEntity<?> getEnglishTests() {
        List<EnglishTest> englishTests = englishTestRepository.findAll();
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
    public ResponseEntity<?> submitTest() {
        return null;
    }

    @Override
    public ResponseEntity<?> insertQuestionToEnglishTest(QuestionPostDto questionPostDto) {
        EnglishTest englishTest = englishTestRepository.findById(questionPostDto.getEnglishTestId())
                .orElseThrow(() -> new NotFoundException("english test not found"));
        if (questionPostDto.getOptions().size() > englishTest.getNumberOfQuestions()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST, "number of questions exceed the maxium number of questions of the english test"));
        }
        Question question = questionMapper.toEntity(questionPostDto);
        question = questionRepository.save(question);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "insert question to english test", questionMapper.toDto(question)));
    }
}
