package com.example.EnglishAppAPI.services.impls;

import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.entities.indexes.UserDocument;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.mapstruct.dtos.NotificationDto;
import com.example.EnglishAppAPI.mapstruct.dtos.NotificationPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.UserInformationDto;
import com.example.EnglishAppAPI.mapstruct.dtos.UserProfileDto;
import com.example.EnglishAppAPI.mapstruct.enums.NotificationType;
import com.example.EnglishAppAPI.mapstruct.mappers.UserMapper;
import com.example.EnglishAppAPI.repositories.elas.UserDocumentRepository;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.responses.ApiResponseStatus;
import com.example.EnglishAppAPI.repositories.UserRepository;
import com.example.EnglishAppAPI.services.interfaces.IUserService;
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
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;
    private final NotificationService notificationService;
    private final UserMapper userMapper;
    private final UserDocumentRepository userDocumentRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public UserService(UserRepository userRepository, CloudinaryService cloudinaryService, NotificationService notificationService, UserMapper userMapper, UserDocumentRepository userDocumentRepository, SimpMessagingTemplate simpMessagingTemplate) {
        this.userRepository = userRepository;
        this.cloudinaryService = cloudinaryService;
        this.notificationService = notificationService;
        this.userMapper = userMapper;
        this.userDocumentRepository = userDocumentRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public ResponseEntity<ApiResponse> followUsers(Long currentUserId, Long id) {
        UserEntity user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new NotFoundException("user is not authorized or not existed"));
        UserEntity followedUser = userRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("followedUser is not existed"));
        assert user != null;

        boolean isFollowFeature = false;

        if (followedUser.getFollowers().contains(user)) {
            followedUser.getFollowers().remove(user);
            followedUser.setFollowersCount(followedUser.getFollowersCount() - 1);
        } else {
            followedUser.getFollowers().add(user);
            followedUser.setFollowersCount(followedUser.getFollowersCount() + 1);
            isFollowFeature = true;
        }
        if (user.getFollowing().contains(followedUser)) {
            user.getFollowing().remove(followedUser);
            user.setFollowingCount(user.getFollowingCount() - 1);
        } else {
            user.getFollowing().add(followedUser);
            user.setFollowingCount(user.getFollowingCount() + 1);
            isFollowFeature = true;
        }

        user = userRepository.save(user);
        followedUser = userRepository.save(followedUser);
        updateUser(followedUser);
        updateUser(user);

        if (isFollowFeature) {
            NotificationDto notificationDto = notificationService.addNotification(new NotificationPostDto(currentUserId, id, user.getFullName() + "is following you now", false, NotificationType.follow ,currentUserId, currentUserId));
            simpMessagingTemplate.convertAndSend("topic/user/notification/" + followedUser.getUserId(), notificationDto);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, isFollowFeature ? "follow user" : "unfollow user", ""));
    }

    @Override
    public ResponseEntity<ApiResponse> unfollowUsers(Long currentUserId, Long id) {
        UserEntity user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new NotFoundException("user is not authorized or not existed"));
        UserEntity followedUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("unfollowedUser is not existed"));
        assert user != null;
        user.getFollowers().remove(followedUser);
        followedUser.getFollowing().remove(user);
        userRepository.save(user);
        userRepository.save(followedUser);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, "unfollow user", ""));
    }

    @Override
    public ResponseEntity<ApiResponse> getUserInfo(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user not found"));
        UserProfileDto userProfileDto = UserProfileDto.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .gender(user.isGender())
                .profilePicture(user.getProfilePicture())
                .followersCount(user.getFollowersCount())
                .followingCount(user.getFollowingCount())
                .reviewsCount(user.getReviews_count())
                .englishLevelName(user.getEnglishLevel().getLevelName())
                .interests(user.getInterests())
                .build();
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get user profile", userProfileDto));
    }

    @Override
    public ResponseEntity<?> changeUserInformation(Long currentUserId, UserInformationDto userInformationDto) {
        UserEntity user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new NotFoundException("user is not authorized or not existed"));
        user.setFullName(userInformationDto.getFullName());
        Map<String, Object> result = null;
        try {
            if (!Objects.equals(userInformationDto.getProfilePicture(), "")) {
                result = cloudinaryService.uploadFile(userInformationDto.getProfilePicture(), "user-"+user.getUserId(), true, true);
                if (!result.containsKey("public_id")) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(ApiResponseStatus.FAIL, "Failed to upload image" + result.get("error").toString(), ""));
                }
                String fileUrl = String.format("https://res.cloudinary.com/daszajz9a/image/upload/v%s/%s", result.get("version"), result.get("public_id"));
                user.setProfilePicture(fileUrl);
            }
            user = userRepository.save(user);
            updateUser(user);
            return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "changed user information", userMapper.toNecessaryDto(user)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> getFollowers(Long currentUserId, int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<UserEntity> page = userRepository.getFollowers(currentUserId, pageable);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get followers", page.map(userMapper::toNecessaryDto)));
    }

    @Override
    public ResponseEntity<?> getFollowing(Long currentUserId, int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<UserEntity> page = userRepository.getFollowing(currentUserId, pageable);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "get followers", page.map(userMapper::toNecessaryDto)));
    }

    @Override
    public ResponseEntity<?> checkIfExist(Long currentUserId, Long id) {
        UserEntity currentUser = userRepository.findById(currentUserId)
                .orElse(null);
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(ApiResponseStatus.FAIL, "can find the current userId", ""));
        }
        UserEntity followedUser = userRepository.findById(id)
                .orElse(null);
        if (followedUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(ApiResponseStatus.FAIL, "can find the followed user", ""));
        }
        boolean status = followedUser.getFollowers().contains(currentUser) || currentUser.getFollowing().contains(followedUser);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "check if the followed user exists", status));
    }

    private void updateUser(UserEntity user) {
        Optional<UserDocument> optionalUserDocument = userDocumentRepository.findById(user.getUserId());
        if (optionalUserDocument.isPresent()) {
            UserDocument userDocument = optionalUserDocument.get();
            userDocument.setFullName(user.getFullName());
            userDocument.setQuote(user.getQuote());
            userDocument.setProfilePicture(user.getProfilePicture());
            userDocument.setFollowersCount(user.getFollowersCount());
            userDocument.setFollowingCount(user.getFollowingCount());
            userDocumentRepository.save(userDocument);
        }
    }
}
