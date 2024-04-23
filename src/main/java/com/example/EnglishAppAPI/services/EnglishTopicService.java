package com.example.EnglishAppAPI.services;

import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTopicDto;
import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTopicPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTopicQuestionDto;
import com.example.EnglishAppAPI.entities.EnglishLevel;
import com.example.EnglishAppAPI.entities.EnglishTopic;
import com.example.EnglishAppAPI.entities.EnglishTopicQuestion;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.mapstruct.dtos.EnglishTopicQuestionPostDto;
import com.example.EnglishAppAPI.mapstruct.mappers.EnglishTopicMapper;
import com.example.EnglishAppAPI.mapstruct.mappers.EnglishTopicQuestionMapper;
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
    public EnglishTopicService(EnglishTopicRepository englishTopicRepository, EnglishTopicQuestionRepository englishTopicQuestionRepository, EnglishLevelRepository englishLevelRepository, EnglishTopicMapper englishTopicMapper, EnglishTopicQuestionMapper englishTopicQuestionMapper) {
        this.englishTopicRepository = englishTopicRepository;
        this.englishTopicQuestionRepository = englishTopicQuestionRepository;
        this.englishLevelRepository = englishLevelRepository;
        this.englishTopicMapper = englishTopicMapper;
        this.englishTopicQuestionMapper = englishTopicQuestionMapper;
    }
    private final EnglishTopicRepository englishTopicRepository;
    private final EnglishTopicQuestionRepository englishTopicQuestionRepository;
    private final EnglishLevelRepository englishLevelRepository;
    private final EnglishTopicMapper englishTopicMapper;
    private final EnglishTopicQuestionMapper englishTopicQuestionMapper;
    @Override
    public ResponseEntity<?> insertNewTopic(EnglishTopicPostDto englishTopicDto) {
        EnglishLevel englishLevel = englishLevelRepository.findById(englishTopicDto.getEnglishLevelId())
                .orElseThrow(() -> new NotFoundException("invalid english level id"));
        EnglishTopic topic = EnglishTopic.builder()
                .englishLevel(englishLevel)
                .header(englishTopicDto.getHeader())
                .content(englishTopicDto.getContent())
                .build();
        englishTopicRepository.save(topic);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ApiResponseStatus.SUCCESS, "inserted new topic successfully", englishTopicMapper.toDto(topic)));
    }

    @Override
    public ResponseEntity<ApiResponse> retrieveAllTopics() {
        List<EnglishTopicDto> topics = englishTopicRepository.findAll().stream().map(englishTopicMapper::toDto).toList();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, "get all topics", topics));
    }

    @Override
    public ResponseEntity<ApiResponse> retrieveAllTopicsInLevel(Long levelId) {
        EnglishLevel englishLevel = englishLevelRepository.findById(levelId).orElseThrow(() -> new NotFoundException("not found english level with the provided id"));
        List<EnglishTopicDto> englishTopics = englishTopicRepository.findByEnglishLevel(englishLevel).stream().map(englishTopicMapper::toDto).toList();
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get all topics with provided level", englishTopics));
    }

    @Override
    public ResponseEntity<?> insertTopicQuestion(EnglishTopicQuestionPostDto englishTopicQuestionDto) {
        englishTopicRepository.findById(englishTopicQuestionDto.getTopicId())
                .orElseThrow(() -> new NotFoundException("no topic is found"));
        EnglishTopicQuestion question = englishTopicQuestionMapper.toEntityQ(englishTopicQuestionDto);
        englishTopicQuestionRepository.save(question);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ApiResponseStatus.SUCCESS,"inserted new question", englishTopicQuestionMapper.toDtoQ(question)));
    }

    @Override
    public ResponseEntity<ApiResponse> retrieveAllQuestionsOfATopic(Long topicId) {
        EnglishTopic topic = englishTopicRepository.findById(topicId).orElseThrow(() -> new NotFoundException("cannot find the topic with the provided id"));
        List<EnglishTopicQuestionDto> questions = englishTopicQuestionRepository.findByTopic(topic).stream().map(englishTopicQuestionMapper::toDtoQ).toList();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, "get all questions", questions));
    }
}
