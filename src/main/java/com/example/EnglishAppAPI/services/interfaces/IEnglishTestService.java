package com.example.EnglishAppAPI.services.interfaces;

import com.example.EnglishAppAPI.entities.Question;
import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTestPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.QuestionPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.SubmitTestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface IEnglishTestService {
    ResponseEntity<?> createEnglishTest(EnglishTestPostDto englishTestPostDto);
    ResponseEntity<?> modifyEnglishTest(EnglishTestPostDto englishTestPostDto);
    ResponseEntity<?> deleteEnglishTest(Long id);
    ResponseEntity<?> getEnglishTests();
    ResponseEntity<?> getQuestions(Long testId);
    ResponseEntity<?> submitTest(SubmitTestDto submitTestDto);
    ResponseEntity<?> insertQuestionToEnglishTest(QuestionPostDto questionPostDto);
}
