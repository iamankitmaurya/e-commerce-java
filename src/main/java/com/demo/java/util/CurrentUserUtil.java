package com.demo.java.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.demo.java.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CurrentUserUtil {

    private final JwtService jwtService;

    private final HttpServletRequest request;

    public String getToken() {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null &&
                authHeader.startsWith("Bearer ")) {

            return authHeader.substring(7);
        }

        return null;
    }

    public String getEmail() {

        return jwtService.extractEmail(getToken());
    }

    public Long getUserId() {

        return jwtService.extractUserId(getToken());
    }

    public String getRole() {

        return jwtService.extractRole(getToken());
    }

    public Authentication getAuthentication() {

        return SecurityContextHolder
                .getContext()
                .getAuthentication();
    }

    public boolean isAdmin() {

        return "ADMIN".equals(getRole());
    }

    public boolean isSeller() {

        return "SELLER".equals(getRole());
    }

    public boolean isCustomer() {

        return "CUSTOMER".equals(getRole());
    }
}