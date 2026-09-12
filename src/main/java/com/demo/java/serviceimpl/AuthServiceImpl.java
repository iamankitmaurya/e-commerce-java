package com.demo.java.serviceimpl;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.java.dto.LoginRequest;
import com.demo.java.dto.LoginResponse;
import com.demo.java.entity.User;
import com.demo.java.repository.UserRepository;
import com.demo.java.service.AuthService;
import com.demo.java.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

        private final UserRepository userRepository;

        private final JwtService jwtService;

        private final PasswordEncoder passwordEncoder;

        private final HttpServletRequest request;

        @Override
        public String register(User request) {

                if (userRepository.existsByEmail(request.getEmail())) {
                        throw new RuntimeException("Email already exists");
                }

                if (userRepository.existsByMobile(request.getMobile())) {
                        throw new RuntimeException("Mobile already exists");
                }

                request.setPassword(
                                passwordEncoder.encode(
                                                request.getPassword()));

                request.setEnabled(true);
                request.setAccountNonLocked(true);
                request.setAccountNonExpired(true);
                request.setCredentialsNonExpired(true);

                request.setFailedLoginAttempts(0);

                userRepository.save(request);

                return "User Registered Successfully";
        }

        @Override
        public LoginResponse login(LoginRequest request) {

                User user = userRepository.findByEmail(
                                request.getEmail())
                                .orElseThrow(() -> new RuntimeException(
                                                "Invalid Email"));

                if (!user.getAccountNonLocked()) {
                        throw new RuntimeException(
                                        "Account Locked");
                }

                if (!passwordEncoder.matches(
                                request.getPassword(),
                                user.getPassword())) {

                        Integer attempts = user.getFailedLoginAttempts() == null
                                        ? 0
                                        : user.getFailedLoginAttempts();

                        attempts++;

                        user.setFailedLoginAttempts(attempts);

                        if (attempts >= 5) {

                                user.setAccountNonLocked(false);
                        }

                        userRepository.save(user);

                        throw new RuntimeException(
                                        "Invalid Password");
                }

                user.setFailedLoginAttempts(0);
                user.setLastLoginAt(LocalDateTime.now());

                String accessToken = jwtService.generateAccessToken(user);

                String refreshToken = jwtService.generateRefreshToken(user);

                user.setRefreshToken(refreshToken);

                userRepository.save(user);

                return new LoginResponse(
                                accessToken,
                                refreshToken,
                                user.getId(),
                                user.getEmail(),
                                user.getRole());
        }

        @Override
        public LoginResponse refreshToken(
                        String refreshToken) {

                User user = userRepository
                                .findByEmail(
                                                jwtService.extractEmail(
                                                                refreshToken))
                                .orElseThrow(() -> new RuntimeException(
                                                "User Not Found"));

                if (!refreshToken.equals(
                                user.getRefreshToken())) {

                        throw new RuntimeException(
                                        "Invalid Refresh Token");
                }

                String newAccessToken = jwtService.generateAccessToken(user);

                return new LoginResponse(
                                newAccessToken,
                                refreshToken,
                                user.getId(),
                                user.getEmail(),
                                user.getRole());
        }

        @Override
        public void logout() {

                // Stateless JWT Logout
        }

        @Override
        public User getCurrentUser() {

                String authHeader = request.getHeader("Authorization");

                if (authHeader == null ||
                                !authHeader.startsWith("Bearer ")) {

                        throw new RuntimeException(
                                        "Authorization header missing");
                }

                String token = authHeader.substring(7);

                Long userId = jwtService.extractUserId(token);

                return userRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException(
                                                "User not found"));
        }
}