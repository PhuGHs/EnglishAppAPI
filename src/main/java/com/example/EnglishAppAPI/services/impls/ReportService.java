package com.example.EnglishAppAPI.services.impls;

import com.example.EnglishAppAPI.mapstruct.dtos.NotificationDto;
import com.example.EnglishAppAPI.mapstruct.dtos.NotificationPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.ReportPostDto;
import com.example.EnglishAppAPI.entities.Report;
import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.mapstruct.enums.NotificationType;
import com.example.EnglishAppAPI.mapstruct.mappers.ReportMapper;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.responses.ApiResponseStatus;
import com.example.EnglishAppAPI.repositories.ReportRepository;
import com.example.EnglishAppAPI.repositories.UserRepository;
import com.example.EnglishAppAPI.services.interfaces.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ReportService implements IReportService {
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;
    private final CloudinaryService cloudinaryService;
    private final NotificationService notificationService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ReportService(UserRepository userRepository, ReportRepository reportRepository, ReportMapper reportMapper, CloudinaryService cloudinaryService, NotificationService notificationService, SimpMessagingTemplate simpMessagingTemplate) {
        this.userRepository = userRepository;
        this.reportRepository = reportRepository;
        this.reportMapper = reportMapper;
        this.cloudinaryService = cloudinaryService;
        this.notificationService = notificationService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public ResponseEntity<ApiResponse> reportOtherUsers(ReportPostDto reportDto) {
        UserEntity user = userRepository.findById(reportDto.getReporterId())
                .orElseThrow(() -> new NotFoundException("current user is not authorized or not existed"));
        UserEntity reported = userRepository.findById(reportDto.getReportedId())
                .orElseThrow(() -> new NotFoundException("can not find user with the reported id"));
        Report report = Report.builder()
                .evidenceImage("")
                .content(reportDto.getContent())
                .reported(reported)
                .reporter(user)
                .reason(reportDto.getReason())
                .createdDate(new Date())
                .build();
        Report report1 = reportRepository.save(report);
        try {
            Map<String, Object> uploadResult = cloudinaryService.uploadFile(reportDto.getEvidenceImage(), "report"+report1.getId().toString(), true, true);
            if (!uploadResult.containsKey("public_id")) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.FAIL, "Failed to upload image" + uploadResult.get("error").toString(), ""));
            }
            String fileUrl = String.format("https://res.cloudinary.com/daszajz9a/image/upload/v%s/%s", uploadResult.get("version"), uploadResult.get("public_id"));
            report1.setEvidenceImage(fileUrl);
            user.getReportsMade().add(report1);
            reported.getReportsReceived().add(report1);
            reportRepository.save(report1);
            userRepository.save(user);
            userRepository.save(reported);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, "create short story", reportMapper.toDto(report1)));
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.FAIL, ex.getMessage(), ""));
        }
    }

    @Override
    public ResponseEntity<ApiResponse> markReportAsSolved(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new NotFoundException("cannot find the report"));
        report.setSolved(true);
        reportRepository.save(report);
        Long userId = report.getReporter().getUserId();
        NotificationDto notificationDto = notificationService.addNotification(new NotificationPostDto(userId, userId, "Your report have recently been reviewed and nothing happened! ", false, NotificationType.REPORT , null, null));
        simpMessagingTemplate.convertAndSend("/topic/user/notification/" + userId, notificationDto);
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
        reported = userRepository.save(reported);
        Long userId = reported.getUserId();
        simpMessagingTemplate.convertAndSend("/topic/user/ban/" + userId, "You are prohibited from accessing our app!");
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, "ban user", reported));
    }

    @Override
    public ResponseEntity<ApiResponse> getAllReports(int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        Page<Report> reports = reportRepository.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, "get all reports", reports.map(reportMapper::toDto)));
    }

    @Override
    public ResponseEntity<ApiResponse> getReport(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Report not found"));
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get report", reportMapper.toDto(report)));
    }
}
