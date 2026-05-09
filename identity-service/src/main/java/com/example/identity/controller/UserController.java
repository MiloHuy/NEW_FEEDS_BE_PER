package com.example.identity.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import com.example.identity.dto.API.AType;
import com.example.identity.dto.req.UpdateProfileRequest;
import com.example.identity.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public AType getMyProfile(@RequestHeader("X-User-Id") String username) {
        log.info("username: {}", username);
        return userService.getUserProfile(username);
    }

    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public AType updateMyProfile(
            @RequestHeader("X-User-Id") String username,
            @RequestBody UpdateProfileRequest request) {
        log.info("username: {}, request: {}", username, request);
        return userService.updateProfile(username, request);
    }
}
