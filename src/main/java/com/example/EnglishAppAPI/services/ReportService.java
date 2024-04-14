package com.example.EnglishAppAPI.services;

import com.example.EnglishAppAPI.mapstruct.dtos.ReportDto;
import com.example.EnglishAppAPI.entities.Report;
import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.responses.ApiResponseStatus;
import com.example.EnglishAppAPI.repositories.ReportRepository;
import com.example.EnglishAppAPI.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService implements IReportService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReportRepository reportRepository;
    @Override
    public ResponseEntity<ApiResponse> reportOtherUsers(ReportDto reportDto) {
        UserEntity user = userRepository.findById(reportDto.getReporterId())
                .orElseThrow(() -> new NotFoundException("current user is not authorized or not existed"));
        UserEntity reported = userRepository.findById(reportDto.getReportedId())
                .orElseThrow(() -> new NotFoundException("can not find user with the reported id"));
        Report report = Report.builder()
                .evidenceImage(reportDto.getEvidenceImage())
                .content(reportDto.getContent())
                .reported(reported)
                .reporter(user)
                .build();
        user.getReportsMade().add(report);
        reported.getReportsReceived().add(report);
        reportRepository.save(report);
        userRepository.save(user);
        userRepository.save(reported);
        //send notifications
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(ApiResponseStatus.SUCCESS, "report user", report));
    }

    @Override
    public ResponseEntity<ApiResponse> markReportAsSolved(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new NotFoundException("cannot find the report"));
        report.setSolved(true);
        reportRepository.save(report);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, "mark as solved", report));
    }

    @Override
    public ResponseEntity<ApiResponse> banUser(Long reportId, Long reportedUserId) {
        UserEntity reported = userRepository.findById(reportedUserId)
                .orElseThrow(() -> new NotFoundException("cannot find the reported user id"));
        reported.setBanned(true);
        Report report = reportRepository.findById(reportId)
                        .orElseThrow(() -> new NotFoundException("cannot find the report"));
        report.setSolved(true);
        userRepository.save(reported);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, "ban user", reported));
    }

    @Override
    public ResponseEntity<ApiResponse> getAllReports() {
        List<Report> reports = reportRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, "get all reports", reports));
    }
}
