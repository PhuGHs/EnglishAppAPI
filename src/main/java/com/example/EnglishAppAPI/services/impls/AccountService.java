package com.example.EnglishAppAPI.services.impls;

import com.example.EnglishAppAPI.configuration.ThreadPoolTaskSchedulerConfig;
import com.example.EnglishAppAPI.configuration.security.JwtGenerator;
import com.example.EnglishAppAPI.entities.EnglishLevel;
import com.example.EnglishAppAPI.entities.indexes.UserDocument;
import com.example.EnglishAppAPI.exceptions.ErrorResponse;
import com.example.EnglishAppAPI.mapstruct.dtos.EmailVerificationDto;
import com.example.EnglishAppAPI.mapstruct.dtos.LoginDto;
import com.example.EnglishAppAPI.mapstruct.dtos.RegisterDto;
import com.example.EnglishAppAPI.entities.Account;
import com.example.EnglishAppAPI.entities.Role;
import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.exceptions.UnauthorizedException;
import com.example.EnglishAppAPI.mapstruct.mappers.AccountMapper;
import com.example.EnglishAppAPI.mapstruct.mappers.InterestMapper;
import com.example.EnglishAppAPI.repositories.EnglishLevelRepository;
import com.example.EnglishAppAPI.repositories.elas.UserDocumentRepository;
import com.example.EnglishAppAPI.responses.ApiResponse;
import com.example.EnglishAppAPI.responses.ApiResponseStatus;
import com.example.EnglishAppAPI.responses.AuthResponse;
import com.example.EnglishAppAPI.repositories.AccountRepository;
import com.example.EnglishAppAPI.repositories.RoleRepository;
import com.example.EnglishAppAPI.repositories.UserRepository;
import com.example.EnglishAppAPI.services.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountService implements IAccountService {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserDocumentRepository userDocumentRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final EnglishLevelRepository englishLevelRepository;
    private final CloudinaryService cloudinaryService;
    private final AccountMapper accountMapper;
    private final MissionService missionService;
    private final InterestMapper interestMapper;
    private final EnglishTestService englishTestService;
    private final ThreadPoolTaskScheduler taskScheduler;
    @Autowired
    private EmailService emailService;

    @Autowired
    public AccountService(AccountRepository accountRepository, RoleRepository roleRepository, UserRepository userRepository, UserDocumentRepository userDocumentRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtGenerator jwtGenerator, EnglishLevelRepository englishLevelRepository, CloudinaryService cloudinaryService, AccountMapper accountMapper, MissionService missionService, InterestMapper interestMapper, EnglishTestService englishTestService, ThreadPoolTaskScheduler taskScheduler) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userDocumentRepository = userDocumentRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.englishLevelRepository = englishLevelRepository;
        this.cloudinaryService = cloudinaryService;
        this.accountMapper = accountMapper;
        this.missionService = missionService;
        this.interestMapper = interestMapper;
        this.englishTestService = englishTestService;
        this.taskScheduler = taskScheduler;
    }

    @Override
    public ResponseEntity<ApiResponse> register(RegisterDto registerDto) throws IOException {
        if (accountRepository.existsByEmail(registerDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(ApiResponseStatus.FAIL, "Email is taken by other users", ""));
        }

        if (!registerDto.getPassword().equals(registerDto.getConfirmedPassword())) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ApiResponse(ApiResponseStatus.FAIL, "Passwords are not match!!", ""));
        }

        UserEntity user = new UserEntity(registerDto.getFullName(), registerDto.getIsMale());
        EnglishLevel level = englishLevelRepository.findById(1L)
                .orElseThrow(() -> new NotFoundException("english level not found"));
        user.setEnglishLevel(level);
        user = userRepository.save(user);

        Map<String, Object> result = cloudinaryService.uploadFile("https://res.cloudinary.com/daszajz9a/image/upload/v1716735453/user-https:/res.cloudinary.com/daszajz9a/image/upload/v1716391456/user-avatar-2.png", "user-avatar-"+ user.getUserId().toString(), true, true);
        if (!result.containsKey("public_id")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(ApiResponseStatus.FAIL, "Failed to upload image" + result.get("error").toString(), ""));
        }
        String fileUrl = String.format("https://res.cloudinary.com/daszajz9a/image/upload/v%s/%s", result.get("version"), result.get("public_id"));
        user.setProfilePicture(fileUrl);
        user = userRepository.save(user);
        userDocumentRepository.save(UserDocument.fromUserEntity(user, user.getInterests().stream().map(interestMapper::toDto).toList()));
        Account account = new Account(registerDto.getEmail(), passwordEncoder.encode(registerDto.getPassword()));
        Role role = roleRepository.findByRoleName(Role.LEARNER).orElseThrow(() -> new NotFoundException("cannot find the role"));
        account.setRole(role);
        account.setActive(false);
        account.setUser(user);
        account = accountRepository.save(account);

        missionService.addMissions(user.getUserId());
        englishTestService.insertTestsToUsers(user.getUserId());

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, "Created account successfully!", accountMapper.toDto(account)));
    }

    @Override
    public ResponseEntity<ApiResponse> registerAdminAccount(RegisterDto registerDto) throws IOException {
        if (accountRepository.existsByEmail(registerDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(ApiResponseStatus.FAIL, "Email is taken by other users", ""));
        }

        if (!registerDto.getPassword().equals(registerDto.getConfirmedPassword())) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ApiResponse(ApiResponseStatus.FAIL, "Passwords are not match!!", ""));
        }
        UserEntity user = new UserEntity(registerDto.getFullName(), registerDto.getIsMale());
        user = userRepository.save(user);
        Map<String, Object> result = cloudinaryService.uploadFile("https://cdn.icon-icons.com/icons2/1378/PNG/512/avatardefault_92824.png", "user-avatar-"+ user.getUserId().toString(), true, true);
        if (!result.containsKey("public_id")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(ApiResponseStatus.FAIL, "Failed to upload image" + result.get("error").toString(), ""));
        }
        String fileUrl = String.format("https://res.cloudinary.com/daszajz9a/image/upload/v%s/%s", result.get("version"), result.get("public_id"));
        user.setProfilePicture(fileUrl);
        user = userRepository.save(user);
        Account account = new Account(registerDto.getEmail(), passwordEncoder.encode(registerDto.getPassword()));
        Role role = roleRepository.findByRoleName(Role.ADMIN).orElseThrow(() -> new NotFoundException("cannot find the role"));
        account.setRole(role);
        account.setUser(user);
        account = accountRepository.save(account);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, "Created admin account successfully!", accountMapper.toDto(account)));
    }

    @Override
    public ResponseEntity<ApiResponse> login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Optional<Account> account = accountRepository.findByEmail(loginDto.getEmail());
        if (account.isEmpty()) {
            throw new NotFoundException("Account is not found");
        }
        String token = jwtGenerator.generateToken(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(ApiResponseStatus.SUCCESS, "Login successfully!", new AuthResponse(token, accountMapper.toDto(account.get()))));
    }

    @Override
    public ResponseEntity<UserEntity> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("User is not authenticated");
        }
        Object principal = authentication.getPrincipal();
        String email = ((UserDetails)principal).getUsername();
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("email is not existed"));
        return ResponseEntity.status(HttpStatus.OK).body(account.getUser());
    }

    @Override
    public ResponseEntity<String> resetPasswordWhenLoggedIn(LoginDto loginDto) {
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = new User(loginDto.getEmail(), loginDto.getPassword(), currentAuth.getAuthorities());
        Authentication newAuth = new UsernamePasswordAuthenticationToken(userDetails, loginDto.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        Account account = accountRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new NotFoundException("email is not existed"));
        account.setPassword(loginDto.getPassword());
        accountRepository.save(account);
        emailService.sendEmail(loginDto.getEmail(), "Changed password successfully!", "Your account password had been changed");
        return ResponseEntity.ok("successful password reset");
    }

    @Override
    public ResponseEntity<String>  resetPasswordWhenForget(LoginDto loginDto) {
        Account account = accountRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new NotFoundException("email is not existed"));
        account.setPassword(loginDto.getPassword());
        accountRepository.save(account);
        emailService.sendEmail(loginDto.getEmail(), "Changed password successfully!", "Your account password had been changed");
        return ResponseEntity.ok("successful password reset");
    }

    @Override
    public ResponseEntity<?> sendVerificationEmail(String email) {
        String verificationCode = generateVerificationCode();
        Account account = accountRepository.findByEmail(email)
                .orElse(null);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND, "cannot find the account with the email provided"));
        }
        account.setVerificationCode(verificationCode);
        accountRepository.save(account);
        emailService.sendEmail(email, "Password reset request", "You have made a request to change your password, here is the code: "+ verificationCode);
        return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "sent email", email));
    }

    @Override
    public ResponseEntity<?> verifyCode(EmailVerificationDto emailVerificationDto) {
        Account account = accountRepository.findByEmail(emailVerificationDto.getEmail())
                .orElse(null);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND, "cannot find the account with the email provided"));
        }
        if (account.getVerificationCode().equals(emailVerificationDto.getCode())) {
            account.setVerificationCode("");
            accountRepository.save(account);
            return ResponseEntity.ok(new ApiResponse(ApiResponseStatus.SUCCESS, "code matched", ""));
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ErrorResponse(HttpStatus.NOT_IMPLEMENTED, "the code are not matched, please try again"));
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }
}
