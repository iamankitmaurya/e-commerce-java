package com.demo.java.service;

import com.demo.java.entity.User;

public interface JwtService {

    String generateAccessToken(User user);

    String generateRefreshToken(User user);

    String extractEmail(String token);

    Long extractUserId(String token);

    String extractRole(String token);

    boolean validateToken(String token);

    Long getCurrentUserId();

    String getCurrentUserEmail();

    String getCurrentUserRole();
}