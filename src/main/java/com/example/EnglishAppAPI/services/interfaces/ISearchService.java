package com.example.EnglishAppAPI.services.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public interface ISearchService {
    ResponseEntity<?> fuzzySearchUserFullName(String userName, Long currentUserId) throws IOException;
    ResponseEntity<?> recommendUsersBasedOnCommonInterests(Long currentUserId, int page, int size) throws IOException;
    ResponseEntity<?> recommendUsersBasedOnEnglishLevel(Long currentUserId, int pageNumber, int pageSize);
}
