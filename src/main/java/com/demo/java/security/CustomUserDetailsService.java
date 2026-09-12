package com.demo.java.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.java.entity.User;
import com.demo.java.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with email : " + email));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())

                .authorities(
                        List.of(
                                new SimpleGrantedAuthority(
                                        "ROLE_" + user.getRole().name())))

                .accountLocked(
                        !Boolean.TRUE.equals(
                                user.getAccountNonLocked()))

                .accountExpired(
                        !Boolean.TRUE.equals(
                                user.getAccountNonExpired()))

                .credentialsExpired(
                        !Boolean.TRUE.equals(
                                user.getCredentialsNonExpired()))

                .disabled(
                        !Boolean.TRUE.equals(
                                user.getEnabled()))

                .build();
    }
}
