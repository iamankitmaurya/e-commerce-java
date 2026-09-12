package com.demo.java.dto;

import com.demo.java.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    private String accessToken;

    private String refreshToken;

    private Long userId;

    private String email;

    private Role role;
}