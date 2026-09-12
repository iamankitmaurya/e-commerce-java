package com.demo.java.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.demo.java.entity.User;
import com.demo.java.exception.DuplicateResourceException;
import com.demo.java.exception.ResourceNotFoundException;
import com.demo.java.repository.UserRepository;
import com.demo.java.response.PageResponse;
import com.demo.java.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException(

                    "User already exists with email : " + user.getEmail());
        }

        if (userRepository.existsByMobile(user.getMobile())) {

            throw new DuplicateResourceException(

                    "User already exists with mobile : " + user.getMobile());

        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setFullName(user.getFullName());
        existingUser.setEmail(user.getEmail());
        existingUser.setMobile(user.getMobile());
        existingUser.setPassword(user.getPassword());

        return userRepository.save(existingUser);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found with id : " + id);
        }

        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "User not found with id : " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserByMobile(String mobile) {
        return userRepository.findByMobile(mobile);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByMobile(String mobile) {
        return userRepository.existsByMobile(mobile);
    }

    @Override
    public PageResponse<User> getUserList(int page, int size) {

        PageRequest pageable = PageRequest.of(page, size);

        Page<User> users = userRepository.findAll(pageable);

        PageResponse<User> response = new PageResponse<>();

        response.setContent(users.getContent());
        response.setCurrentPage(users.getNumber());
        response.setPageSize(users.getSize());
        response.setTotalElements(users.getTotalElements());
        response.setTotalPages(users.getTotalPages());
        response.setFirst(users.isFirst());
        response.setLast(users.isLast());
        response.setHasNext(users.hasNext());
        response.setHasPrevious(users.hasPrevious());

        return response;
    }
}