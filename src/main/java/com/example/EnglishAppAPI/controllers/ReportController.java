package com.example.EnglishAppAPI.controllers;

import com.example.EnglishAppAPI.mapstruct.dtos.ReportPostDto;
import com.example.EnglishAppAPI.mapstruct.enums.ShortStoryOrderBy;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.services.impls.ReportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/reports")
@SecurityRequirement(name = "bearerAuth")
public class ReportController {
    @Autowired
    private ReportService reportService;
    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> getAllReports(
            @RequestParam int pageNumber,
            @RequestParam int pageSize,
            @RequestParam(defaultValue = "createdDate") String sortBy
    ) {
        return reportService.getAllReports(pageNumber, pageSize, sortBy);
    }
    @PostMapping("/report-user")
    @PreAuthorize("hasAuthority('LEARNER')")
    public ResponseEntity<ApiResponse> reportUser(@RequestBody ReportPostDto reportDto) {
        return reportService.reportOtherUsers(reportDto);
    }
    @PutMapping("{reportId}/mark-as-solved")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> markAsSolved(@PathVariable Long reportId) {
        return reportService.markReportAsSolved(reportId);
    }
    @PutMapping("{reportId}/ban-user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> banUser(@PathVariable Long reportId, @RequestParam Long reportedUserId) {
        return reportService.banUser(reportId, reportedUserId);
    }
}
