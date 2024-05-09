package com.example.EnglishAppAPI.services.impls;

import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.mapstruct.dtos.NotificationPostDto;
import com.example.EnglishAppAPI.mapstruct.dtos.UserInformationDto;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.responses.ApiResponseStatus;
import com.example.EnglishAppAPI.repositories.UserRepository;
import com.example.EnglishAppAPI.services.interfaces.IUserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;
    private final NotificationService notificationService;
    @Autowired
    public UserService(UserRepository userRepository, CloudinaryService cloudinaryService, NotificationService notificationService) {
        this.userRepository = userRepository;
        this.cloudinaryService = cloudinaryService;
        this.notificationService = notificationService;
    }

    @Override
    public ResponseEntity<ApiResponse> followUsers(Long currentUserId, Long id) {
        UserEntity user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new NotFoundException("user is not authorized or not existed"));
        UserEntity followedUser = userRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("followedUser is not existed"));
        assert user != null;
        user.getFollowers().add(followedUser);
        followedUser.getFollowing().add(user);
        userRepository.save(user);
        userRepository.save(followedUser);
        notificationService.addNotification(new NotificationPostDto(currentUserId, id, user.getFullName() + "is following you now", false, LocalDateTime.now(), currentUserId, currentUserId));
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, "follow user", ""));
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
        return null;
    }

    @Override
    public ResponseEntity<?> changeUserInformation(Long currentUserId, UserInformationDto userInformationDto) {
        UserEntity user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new NotFoundException("user is not authorized or not existed"));
        user.setFullName(userInformationDto.getFullName());
        user.setGender(userInformationDto.isGender());
        user.setQuote(userInformationDto.getQuote());
        Map<String, Object> result = null;
        try {
            result = cloudinaryService.uploadFile(userInformationDto.getProfilePicture(), "user-"+user.getProfilePicture(), true, true);
            if (!result.containsKey("public_id")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(ApiResponseStatus.FAIL, "Failed to upload image" + result.get("error").toString(), ""));
            }
            String fileUrl = String.format("https://res.cloudinary.com/daszajz9a/image/upload/v%s/%s", result.get("version"), result.get("public_id"));
            user.setProfilePicture(fileUrl);
            return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "changed user information", user));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
