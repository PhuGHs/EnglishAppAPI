package com.example.EnglishAppAPI.services.impls;

import com.example.EnglishAppAPI.exceptions.ErrorResponse;
import com.example.EnglishAppAPI.mapstruct.dtos.ShortStoryPostDto;
import com.example.EnglishAppAPI.entities.ShortStory;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.mapstruct.enums.ShortStoryOrderBy;
import com.example.EnglishAppAPI.mapstruct.mappers.ShortStoryMapper;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.responses.ApiResponseStatus;
import com.example.EnglishAppAPI.repositories.ShortStoryRepository;
import com.example.EnglishAppAPI.services.interfaces.IShortStoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ShortStoryService implements IShortStoryService {
    @Autowired
    private ShortStoryRepository shortStoryRepository;
    @Autowired
    private ShortStoryMapper shortStoryMapper;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Override
    public ResponseEntity<ApiResponse> createNewShortStories(ShortStoryPostDto shortStoryPostDto) {
        try {
            ShortStory story = shortStoryMapper.toEntity(new ShortStoryPostDto(shortStoryPostDto.getTitle(), shortStoryPostDto.getParagraph(), ""));
            story.setCreatedDate(new Date());
            ShortStory savedStory = shortStoryRepository.save(story);
            Map<String, Object> result = cloudinaryService.uploadFile(shortStoryPostDto.getImage(), "short-story"+ savedStory.getId().toString(), true, true);
            if (!result.containsKey("public_id")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(ApiResponseStatus.FAIL, "Failed to upload image" + result.get("error").toString(), ""));
            }
            String fileUrl = String.format("https://res.cloudinary.com/daszajz9a/image/upload/v%s/%s", result.get("version"), result.get("public_id"));
            savedStory.setImage(fileUrl);
            shortStoryRepository.save(savedStory);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ApiResponseStatus.SUCCESS, "create short story", shortStoryMapper.toDto(savedStory)));
        } catch (IOException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(ApiResponseStatus.FAIL, exception.getMessage(), ""));
        }
    }

    @Override
    public ResponseEntity<?> deleteShortStory(Long id) {
        if (!shortStoryRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find the short story");
        }
        shortStoryRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Delete successfully");
    }

    @Override
    public ResponseEntity<ApiResponse> updateShortStory(Long id, ShortStoryPostDto shortStoryPostDto) {
        ShortStory shortStory = shortStoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("cannot find the short story"));
        shortStory.setTitle(shortStoryPostDto.getTitle());
        shortStory.setParagraph(shortStoryPostDto.getParagraph());
        shortStory.setImage(shortStoryPostDto.getImage());
        shortStoryRepository.save(shortStory);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, "Update a short story", shortStoryMapper.toDto(shortStory)));
    }

    @Override
    public ResponseEntity<ApiResponse> getAllShortStories(int pageNumber, int pageSize, ShortStoryOrderBy sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy.toString()).descending());
        Page<ShortStory> shortStories = shortStoryRepository.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, "Get all short stories", shortStories.map(shortStoryMapper::toDto)));
    }

    @Override
    public ResponseEntity<ApiResponse> likeStory(Long id) {
        ShortStory shortStory = shortStoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("cannot find the short story"));
        shortStory.setNumberOfLikes(shortStory.getNumberOfLikes() + 1);
        shortStoryRepository.save(shortStory);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, "like story", shortStoryMapper.toDto(shortStory)));
    }

    @Override
    public ResponseEntity<?> getShortStory(Long id) {
        ShortStory shortStory = shortStoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("cannot find the short story"));
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get short story", shortStoryMapper.toDto(shortStory)));
    }

    @Override
    public ResponseEntity<?> getRandom5ShortStories(Long shortStoryId) {
        List<ShortStory> stories = shortStoryRepository.get5RandomShortStories(shortStoryId);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get random 5 stories", stories.stream().map(shortStoryMapper::toDto)));
    }
}
