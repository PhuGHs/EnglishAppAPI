package com.example.EnglishAppAPI.services;

import com.example.EnglishAppAPI.dtos.ReportDto;
import com.example.EnglishAppAPI.models.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface IReportService {
    ResponseEntity<ApiResponse> reportOtherUsers(ReportDto reportDto);
    ResponseEntity<ApiResponse> markReportAsSolved(Long reportId);
    ResponseEntity<ApiResponse> banUser(Long reportId, Long reportedUserId);
    ResponseEntity<ApiResponse> getAllReports();
}
