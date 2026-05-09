package com.example.identity.service;

import com.example.identity.database.entity.User;
import com.example.identity.database.repository.UserRepository;
import com.example.identity.dto.API.AType;
import com.example.identity.dto.API.ApiType;
import com.example.identity.dto.API.ErrorType;
import com.example.identity.dto.req.AuthRequest;
import com.example.identity.dto.req.RegisterRequest;
import com.example.identity.dto.res.AuthResponse;
import com.example.identity.exception.AppException;
import com.example.identity.security.JwtProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    public AType login(AuthRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorType.
                    badRequest("Invalid username or password")));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AppException(ErrorType.badRequest("Invalid username or password"));
        }

        String accessToken = jwtProvider.generateToken(user.getUsername(), user.getRole());
        String refreshToken = jwtProvider.generateRefreshToken(user.getUsername());

        redisService.setValueWithExpiry(accessToken , user.getId().toString());

        return ApiType.success(new AuthResponse(accessToken, refreshToken));
    }

    public AType register(RegisterRequest request) {
        // 1 check user duplicate username or email
        
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorType.badRequest("Username already exists"));
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorType.badRequest("Email already exists"));
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstname());
        user.setLastName(request.getLastname());

        userRepository.save(user);

        String accessToken = jwtProvider.generateToken(user.getUsername(), user.getRole());
        String refreshToken = jwtProvider.generateRefreshToken(user.getUsername());

        redisService.setValueWithExpiry(accessToken , user.getId().toString());

        return ApiType.success(new AuthResponse(accessToken, refreshToken));
    }

    public AType refresh(String refreshToken) {
        
        if (!jwtProvider.isTokenValid(refreshToken)) {
            throw new AppException(ErrorType.badRequest("Invalid refresh token"));
        }

        String username = jwtProvider.extractUsername(refreshToken);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorType.badRequest("User not found")));

        String newAccessToken = jwtProvider.generateToken(user.getUsername(), user.getRole());
        String newRefreshToken = jwtProvider.generateRefreshToken(user.getUsername());

        redisService.setValueWithExpiry(newAccessToken , user.getId().toString());

        return ApiType.success(new AuthResponse(newAccessToken, newRefreshToken));
    }
}
