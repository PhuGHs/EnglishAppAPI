package com.example.EnglishAppAPI.services;

import com.example.EnglishAppAPI.configuration.security.JwtGenerator;
import com.example.EnglishAppAPI.dtos.LoginDto;
import com.example.EnglishAppAPI.dtos.RegisterDto;
import com.example.EnglishAppAPI.entities.Account;
import com.example.EnglishAppAPI.entities.Role;
import com.example.EnglishAppAPI.entities.UserEntity;
import com.example.EnglishAppAPI.exceptions.NotFoundException;
import com.example.EnglishAppAPI.models.ApiResponse;
import com.example.EnglishAppAPI.models.AuthResponse;
import com.example.EnglishAppAPI.repositories.AccountRepository;
import com.example.EnglishAppAPI.repositories.RoleRepository;
import com.example.EnglishAppAPI.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService implements IAccountService {
    private AccountRepository accountRepository;
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtGenerator jwtGenerator;

    @Autowired
    public AccountService(AccountRepository accountRepository, RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtGenerator jwtGenerator) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public ResponseEntity<ApiResponse> register(RegisterDto registerDto) {
        if (accountRepository.existsByEmail(registerDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Failed", "Email is taken by other users", ""));
        }

        if (!registerDto.getPassword().equals(registerDto.getConfirmedPassword())) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ApiResponse("Failed", "Passwords are not match!!", ""));
        }
        UserEntity user = new UserEntity(registerDto.getFullName(), registerDto.getIsMale());
        userRepository.save(user);
        Account account = new Account(registerDto.getEmail(), passwordEncoder.encode(registerDto.getPassword()));
        Role role = roleRepository.findByRoleName("USER").get();
        account.setRole(role);
        account.setUser(user);
        accountRepository.save(account);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Succeed", "Created account successfully!", account));
    }

    @Override
    public ResponseEntity<ApiResponse> login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Optional<Account> account =accountRepository.findByEmail(loginDto.getEmail());
        if (account.isEmpty()) {
            throw new NotFoundException("Account is not found");
        }
        String token = jwtGenerator.generateToken(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Succeed", "Login successfully!", new AuthResponse(token)));
    }
}
