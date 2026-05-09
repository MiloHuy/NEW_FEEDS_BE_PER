package com.example.src.services;

import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final ReactiveStringRedisTemplate redisTemplate;

    public Mono<Boolean> saveToken(String token, String userId) {
        return redisTemplate.opsForValue().set(token, userId);
    }

    public Mono<Boolean> hasToken(String token) {
        return redisTemplate.hasKey(token);
    }

    public Mono<String> getToken(String token) {
        return redisTemplate.opsForValue().get(token);
    }

    public Mono<Long> deleteToken(String token) {
        return redisTemplate.delete(token);
    }
}
