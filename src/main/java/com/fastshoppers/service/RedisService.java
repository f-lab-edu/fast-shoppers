package com.fastshoppers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RedisService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void saveRefreshToken(String email, String refreshToken, long expirationTime) {
        ValueOperations<String, String> values= stringRedisTemplate.opsForValue();
        values.set(email, refreshToken, expirationTime, TimeUnit.SECONDS);
    }

   public String getRefreshToken(String email) {
       ValueOperations<String, String> values= stringRedisTemplate.opsForValue();
       return values.get(email);
   }

   public void deleteRefreshToken(String email) {
        stringRedisTemplate.delete(email);
   }

}
