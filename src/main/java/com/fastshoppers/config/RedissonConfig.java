package com.fastshoppers.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedissonConfig {

	@Value("${spring.data.redis.host}")
	private String redisHost;

	@Value("${spring.data.redis.port}")
	private int redisPort;

	/**
	 * description : Redis 관련 설정
	 * @return redissonClient
	 */
	@Bean
	public RedissonClient redissonClient() {
		Config config = new Config();

		final String REDIS_ADDRRES_FORMAT = "redis://%s:%d";

		String redisAddress = String.format(REDIS_ADDRRES_FORMAT, redisHost, redisPort);

		config.useSingleServer().setAddress(redisAddress);
		return Redisson.create(config);
	}

	/**
	 * @description : ConnectionFactory를 연결하고, redisTemplate을 사용자 설정하여 리턴하는 메서드
	 * @param connectionFactory
	 * @return redisTemplate
	 */
	@Bean
	public RedisTemplate<Integer, Integer> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<Integer, Integer> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connectionFactory);
		return redisTemplate;
	}
}
