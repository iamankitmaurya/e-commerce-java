package com.demo.java.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.demo.java.enums.Role;
import com.demo.java.enums.UserStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "mobile")
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String mobile;

    private String password;

    private String profileImage;

    private LocalDate dateOfBirth;

    private String gender;

    private String country;

    private String state;

    private String city;

    private String pincode;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private Boolean emailVerified;

    private Boolean mobileVerified;

    private Boolean accountNonLocked;

    private Boolean accountNonExpired;

    private Boolean credentialsNonExpired;

    private Boolean enabled;

    @OneToMany(mappedBy = "user")
    private List<Product> products;

    private Integer failedLoginAttempts;

    private LocalDateTime lastLoginAt;

    private LocalDateTime passwordChangedAt;

    private String refreshToken;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private Long createdBy;

    private Long updatedBy;
}