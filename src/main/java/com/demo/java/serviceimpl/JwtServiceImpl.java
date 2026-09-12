package com.demo.java.serviceimpl;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.demo.java.entity.User;
import com.demo.java.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtServiceImpl implements JwtService {
        @Autowired
        private HttpServletRequest request;

        @Value("${jwt.secret}")
        private String secret;

        @Value("${jwt.expiration}")
        private long accessTokenExpiration;

        @Value("${jwt.refresh-expiration}")
        private long refreshTokenExpiration;

        private SecretKey getSigningKey() {

                return Keys.hmacShaKeyFor(
                                secret.getBytes(StandardCharsets.UTF_8));
        }

        @Override
        public String generateAccessToken(User user) {

                return Jwts.builder()
                                .subject(user.getEmail())
                                .claim("userId", user.getId())
                                .claim("role", user.getRole().name())
                                .issuedAt(new Date())
                                .expiration(
                                                new Date(
                                                                System.currentTimeMillis()
                                                                                + accessTokenExpiration))
                                .signWith(getSigningKey())
                                .compact();
        }

        @Override
        public String generateRefreshToken(User user) {

                return Jwts.builder()
                                .subject(user.getEmail())
                                .claim("userId", user.getId())
                                .issuedAt(new Date())
                                .expiration(
                                                new Date(
                                                                System.currentTimeMillis()
                                                                                + refreshTokenExpiration))
                                .signWith(getSigningKey())
                                .compact();
        }

        @Override
        public String extractEmail(String token) {

                return extractAllClaims(token)
                                .getSubject();
        }

        @Override
        public Long extractUserId(String token) {

                return extractAllClaims(token)
                                .get("userId", Long.class);
        }

        @Override
        public String extractRole(String token) {

                return extractAllClaims(token)
                                .get("role", String.class);
        }

        @Override
        public boolean validateToken(String token) {

                try {

                        Claims claims = extractAllClaims(token);

                        return claims.getExpiration()
                                        .after(new Date());

                } catch (Exception e) {

                        return false;
                }
        }

        private Claims extractAllClaims(
                        String token) {

                return Jwts.parser()
                                .verifyWith(getSigningKey())
                                .build()
                                .parseSignedClaims(token)
                                .getPayload();
        }

        private String getCurrentToken() {

                String authHeader = request.getHeader("Authorization");

                if (authHeader == null ||
                                !authHeader.startsWith("Bearer ")) {

                        throw new RuntimeException(
                                        "Authorization header missing");
                }

                return authHeader.substring(7);
        }

        @Override
        public Long getCurrentUserId() {

                return extractUserId(
                                getCurrentToken());
        }

        @Override
        public String getCurrentUserEmail() {

                return extractEmail(
                                getCurrentToken());
        }

        @Override
        public String getCurrentUserRole() {

                return extractRole(
                                getCurrentToken());
        }
}