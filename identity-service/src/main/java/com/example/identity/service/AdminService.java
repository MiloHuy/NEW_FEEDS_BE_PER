package com.example.identity.service;

import org.springframework.stereotype.Service;

import com.example.identity.database.entity.User;
import com.example.identity.database.repository.UserRepository;
import com.example.identity.dto.API.AType;
import com.example.identity.dto.API.ApiType;
import com.example.identity.dto.API.ErrorType;
import com.example.identity.exception.AppException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    public AType getAllUsers() {
        return ApiType.success(userRepository.findAll());
    }

    public AType getUserById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorType.notFound("User not found")));
        return ApiType.success(user);
    }

    public AType toggleUserLock(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorType.notFound("User not found")));
        user.setIsLocked(!user.getIsLocked());
        userRepository.save(user);
        return ApiType.success(user);
    }

    public AType toggleUserEnable(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorType.notFound("User not found")));
        user.setIsEnable(!user.getIsEnable());
        userRepository.save(user);
        return ApiType.success(user);
    }

    public AType deleteUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorType.notFound("User not found")));
        user.setIsDeleted(true);
        userRepository.save(user);
        return ApiType.success(user);
    }
}
