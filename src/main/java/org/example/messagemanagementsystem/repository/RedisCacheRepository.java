package org.example.messagemanagementsystem.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisCacheRepository {
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${redis.cache.default-timeout}")
    private long defaultTimeout;

    public void cacheData(String key, Object value) {
        redisTemplate.opsForValue().set(key, value, defaultTimeout, TimeUnit.SECONDS);
    }

    public Object getCachedData(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
