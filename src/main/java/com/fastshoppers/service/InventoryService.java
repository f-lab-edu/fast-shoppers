package com.fastshoppers.service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastshoppers.entity.Product;
import com.fastshoppers.exception.EntityNotFoundException;
import com.fastshoppers.exception.InventoryShortageException;
import com.fastshoppers.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class InventoryService {

	private final RedissonClient redissonClient;

	private final InventoryRedisService inventoryRedisService;

	private final ProductRepository productRepository;

	@Autowired
	public InventoryService(RedissonClient redissonClient, InventoryRedisService inventoryRedisService,
		ProductRepository productRepository) {
		this.redissonClient = redissonClient;
		this.inventoryRedisService = inventoryRedisService;
		this.productRepository = productRepository;
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
			int productId = getProductIdFromUuid(productUuid);

			// 재고 조회 로직 실행
			return inventoryRedisService.getInventory(productId);

		} finally {
			// 락 해제
			lock.unlock();
		}
	}

	/**
	 * @description : 재고 업데이트 로직
	 * @param productUuid
	 * @param quantity
	 * @return
	 */
	@Transactional
	public void updateInventory(String productUuid, int quantity) {
		RLock lock = redissonClient.getLock("inventoryLock:" + productUuid);

		try {
			lock.lock();

			// productUuid가 아닌 내부키 productId조회
			int productId = getProductIdFromUuid(productUuid);

			// mysql 원 디비에서 product 조회
			Product product = productRepository.findById(productId)
				.orElseThrow(() -> new EntityNotFoundException("Product not found with UUID" + productUuid));

			// newStock = 남은재고 + 추가 / 감소할 재고
			int newStock = product.getRemainQuantity() + quantity;

			if (newStock < 0) {
				throw new InventoryShortageException();
			}

			product.setRemainQuantity(newStock);
			// mysql디비에 저장
			productRepository.save(product);

			// redis sync 맞추어 저장
			inventoryRedisService.updateInventory(productId, newStock);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * @description : 외부키인 uuid를 받아서, 상품의 내부키 productId를 조회하는 메서드
	 * @param productUuid
	 * @return
	 */
	public Integer getProductIdFromUuid(String productUuid) {
		return productRepository.findByProductUuid(productUuid)
			.map(Product::getId)
			.orElseThrow(() -> new EntityNotFoundException("Product not found with UUID" + productUuid));
	}
}
