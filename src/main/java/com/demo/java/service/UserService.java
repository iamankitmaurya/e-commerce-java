package com.demo.java.service;

import java.util.List;
import java.util.Optional;

import com.demo.java.entity.User;
import com.demo.java.response.PageResponse;

public interface UserService {

    User saveUser(User user);

    User updateUser(Long id, User user);

    Optional<User> getUserById(Long id);

    List<User> getAllUsers();

    PageResponse<User> getUserList(int page, int size);

    void deleteUser(Long id);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserByMobile(String mobile);

    boolean existsByEmail(String email);

    boolean existsByMobile(String mobile);
}