package com.example.identity.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.identity.dto.API.AType;
import com.example.identity.service.AdminService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public AType getAllUsers() {
        return adminService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public AType getUserById(@PathVariable String id) {
        return adminService.getUserById(id);
    }

    @PostMapping("/users/{id}/toggle-lock")
    @PreAuthorize("hasRole('ADMIN')")
    public AType toggleUserLock(@PathVariable String id) {
        return adminService.toggleUserLock(id);
    }

    @PostMapping("/users/{id}/toggle-enable")
    @PreAuthorize("hasRole('ADMIN')")
    public AType toggleUserEnable(@PathVariable String id) {
        return adminService.toggleUserEnable(id);
    }

    @PostMapping("/users/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public AType deleteUser(@PathVariable String id) {
        return adminService.deleteUser(id);
    }
}
