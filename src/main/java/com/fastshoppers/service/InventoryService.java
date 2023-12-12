package com.fastshoppers.service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

	private final RedissonClient redissonClient;

	private final InventoryRedisService inventoryRedisService;

	@Autowired
	public InventoryService(RedissonClient redissonClient, InventoryRedisService inventoryRedisService) {
		this.redissonClient = redissonClient;
		this.inventoryRedisService = inventoryRedisService;
	}

	/**
	 * @description : 락을 획득해서, 재고 조회 로직을 실행한다.
	 * @param productUuid
	 * @return
	 */
	public int getInventory(String productUuid) {
		// Lock 생성
		RLock lock = redissonClient.getLock("inventoryLock:" + productUuid);
		try {
			// 락 획득
			lock.lock();

			// productUuid가 아닌 내부키 productId조회
			Integer productId = 0; // 이 부분 추후 수정

			// 재고 조회 로직 실행
			int inventory = inventoryRedisService.getInventory(productId);

			return inventory;
		} finally {
			// 락 해제
			lock.unlock();
		}
	}

	// 두 번째로 재고 감소시키는 서비스도 만들기
}
