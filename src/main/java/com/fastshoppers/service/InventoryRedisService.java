package com.fastshoppers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @description : 재고 조회 RedisService
 */
@Service
public class InventoryRedisService {
	private final RedisTemplate<Integer, Integer> redisTemplate;

	@Autowired
	public InventoryRedisService(RedisTemplate<Integer, Integer> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * @description : 재고 조회 로직
	 * @param productId
	 * @return
	 */
	public int getInventory(Integer productId) {
		Integer inventory = redisTemplate.opsForValue().get(productId);
		return inventory != null ? inventory : 0; // 없으면 0 리턴
	}

	/**
	 * @description : 재고 갱신 로직
	 * @param productId
	 * @param newInventory
	 */
	public void updateInventory(Integer productId, int newInventory) {
		redisTemplate.opsForValue().set(productId, newInventory);
	}

}
