package com.demo.java.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.java.dto.LoginRequest;
import com.demo.java.dto.LoginResponse;
import com.demo.java.entity.User;
import com.demo.java.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

        private final AuthService authService;

        @PostMapping("/register")
        public ResponseEntity<String> register(
                        @RequestBody User user) {

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(authService.register(user));
        }

        @PostMapping("/login")
        public ResponseEntity<LoginResponse> login(
                        @RequestBody LoginRequest request) {
                System.out.println("dg");
                return ResponseEntity.ok(
                                authService.login(request));
        }

        @PostMapping("/refresh-token")
        public ResponseEntity<LoginResponse> refreshToken(
                        @RequestParam String refreshToken) {

                return ResponseEntity.ok(
                                authService.refreshToken(refreshToken));
        }

        @PostMapping("/logout")
        public ResponseEntity<String> logout() {

                authService.logout();

                return ResponseEntity.ok(
                                "Logout Successful");
        }

        @GetMapping("/me")
        public ResponseEntity<User> getCurrentUser() {

                return ResponseEntity.ok(
                                authService.getCurrentUser());
        }

}