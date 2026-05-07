package com.example.identity.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import com.example.identity.dto.API.AType;
import com.example.identity.dto.req.UpdateProfileRequest;
import com.example.identity.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public AType getMyProfile(@RequestHeader("X-User-Id") String userId) {
        return userService.getUserProfile(userId);
    }

    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public AType updateMyProfile(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody UpdateProfileRequest request) {
        return userService.updateProfile(userId, request);
    }
}
