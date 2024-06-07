package com.example.EnglishAppAPI.services.interfaces;

import com.example.EnglishAppAPI.mapstruct.dtos.ReportPostDto;
import com.example.EnglishAppAPI.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface IReportService {
    ResponseEntity<ApiResponse> reportOtherUsers(ReportPostDto reportDto);
    ResponseEntity<ApiResponse> markReportAsSolved(Long reportId);
    ResponseEntity<ApiResponse> banUser(Long reportId, Long reportedUserId);
    ResponseEntity<ApiResponse> getAllReports(int pageNumber, int pageSize, String sortBy);
    ResponseEntity<ApiResponse> getReport(Long id);
}
