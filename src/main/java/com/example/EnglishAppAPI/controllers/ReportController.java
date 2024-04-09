package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.dtos.ReportDto;
import com.example.EnglishAppAPI.models.ApiResponse;
import com.example.EnglishAppAPI.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/reports")
public class ReportController {
    @Autowired
    private ReportService reportService;
    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllReports() {
        return reportService.getAllReports();
    }
    @PostMapping("/report-user")
    public ResponseEntity<ApiResponse> reportUser(@RequestBody ReportDto reportDto) {
        return reportService.reportOtherUsers(reportDto);
    }
    @PutMapping("{reportId}/mark-as-solved")
    public ResponseEntity<ApiResponse> markAsSolved(@PathVariable Long reportId) {
        return reportService.markReportAsSolved(reportId);
    }
    @PutMapping("{reportId}/ban-user")
    public ResponseEntity<ApiResponse> banUser(@PathVariable Long reportId, @RequestParam Long reportedUserId) {
        return reportService.banUser(reportId, reportedUserId);
    }
}
