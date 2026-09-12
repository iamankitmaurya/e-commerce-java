package com.demo.java.service;

import com.demo.java.dto.LoginRequest;
import com.demo.java.dto.LoginResponse;
import com.demo.java.entity.User;

public interface AuthService {

    String register(User request);

    LoginResponse login(LoginRequest request);

    LoginResponse refreshToken(String refreshToken);

    void logout();

    User getCurrentUser();
}