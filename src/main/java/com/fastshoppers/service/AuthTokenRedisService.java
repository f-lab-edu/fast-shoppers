package com.fastshoppers.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class AuthTokenRedisService {

	private final StringRedisTemplate stringRedisTemplate;

	@Autowired
	public AuthTokenRedisService(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	public void saveRefreshToken(String email, String refreshToken, long expirationTime) {
		ValueOperations<String, String> values = stringRedisTemplate.opsForValue();
		values.set(email, refreshToken, expirationTime, TimeUnit.SECONDS);
	}

	public String getRefreshToken(String email) {
		ValueOperations<String, String> values = stringRedisTemplate.opsForValue();
		return values.get(email);
	}

	public void deleteRefreshToken(String email) {
		stringRedisTemplate.delete(email);
	}

}
