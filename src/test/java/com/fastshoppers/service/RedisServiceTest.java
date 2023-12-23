package com.fastshoppers.service;

import static org.mockito.Mockito.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class RedisServiceTest {

	@Mock
	private StringRedisTemplate stringRedisTemplate;

	@Mock
	private ValueOperations<String, String> valueOperations;

	@InjectMocks
	private RedisService redisService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);
	}

	@Test
	void saveRefreshToken() {
		String email = "test@google.com";
		String token = "token";
		long expirationTime = 60L;

		redisService.saveRefreshToken(email, token, expirationTime);

		verify(valueOperations).set(email, token, expirationTime, TimeUnit.MILLISECONDS);
	}
}
