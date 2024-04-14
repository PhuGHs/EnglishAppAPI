package com.example.EnglishAppAPI.services;

import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTopicDto;
import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTopicQuestionDto;
import com.example.EnglishAppAPI.entities.EnglishLevel;
import com.example.EnglishAppAPI.entities.EnglishTopic;
import com.example.EnglishAppAPI.entities.EnglishTopicQuestion;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.responses.ApiResponseStatus;
import com.example.EnglishAppAPI.repositories.EnglishLevelRepository;
import com.example.EnglishAppAPI.repositories.EnglishTopicQuestionRepository;
import com.example.EnglishAppAPI.repositories.EnglishTopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class EnglishTopicService implements IEnglishTopicService {
    @Autowired
    private EnglishTopicRepository englishTopicRepository;
    @Autowired
    private EnglishTopicQuestionRepository englishTopicQuestionRepository;
    @Autowired
    private EnglishLevelRepository englishLevelRepository;
    @Override
    public ResponseEntity<?> insertNewTopic(EnglishTopicDto englishTopicDto) {
        EnglishLevel englishLevel = englishLevelRepository.findById(englishTopicDto.getEnglishLevelId())
                .orElseThrow(() -> new NotFoundException("invalid english level id"));
        EnglishTopic topic = EnglishTopic.builder()
                .englishLevel(englishLevel)
                .header(englishTopicDto.getHeader())
                .content(englishTopicDto.getContent())
                .build();
        englishTopicRepository.save(topic);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ApiResponseStatus.SUCCESS, "inserted new topic successfully", topic));
    }

    @Override
    public ResponseEntity<ApiResponse> retrieveAllTopics() {
        List<EnglishTopic> topics = englishTopicRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, "get all topics", topics));
    }

    @Override
    public ResponseEntity<?> insertTopicQuestion(Long topicId, EnglishTopicQuestionDto englishTopicQuestionDto) {
        EnglishTopic englishTopic = englishTopicRepository.findById(topicId)
                .orElseThrow(() -> new NotFoundException("no topic is found"));
        EnglishTopicQuestion question = EnglishTopicQuestion
                .builder()
                .questionName(englishTopicQuestionDto.getQuestion())
                .sampleAnswer(englishTopicQuestionDto.getSampleAnswers())
                .topic(englishTopic)
                .build();
        englishTopicQuestionRepository.save(question);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ApiResponseStatus.SUCCESS,"inserted new question", question));
    }

    @Override
    public ResponseEntity<ApiResponse> retrieveAllQuestionsOfATopic(Long topicId) {
        EnglishTopic topic = englishTopicRepository.findById(topicId)
                .orElseThrow(() -> new NotFoundException("invalid topic id"));
        Set<EnglishTopicQuestion> questions = topic.getQuestions();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, "get all questions", questions));
    }
}
