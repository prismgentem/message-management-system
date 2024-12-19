package org.example.messagemanagementsystem.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ModelRepository {
    private final RedisTemplate<String, byte[]> modelRedisTemplate;

    public void saveModel(String key, byte[] model) {
        modelRedisTemplate.opsForValue().set(key, model);
    }

    public byte[] getModel(String key) {
        return modelRedisTemplate.opsForValue().get(key);
    }

    public void deleteModel(String redisModelKey) {
        modelRedisTemplate.delete(redisModelKey);
    }
}
