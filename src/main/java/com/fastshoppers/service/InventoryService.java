package com.fastshoppers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastshoppers.entity.Product;
import com.fastshoppers.exception.InventoryShortageException;
import com.fastshoppers.exception.ProductEntityNotFoundException;
import com.fastshoppers.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

	private final ProductRepository productRepository;
  
	/**
	 * @description : 락을 획득해서, 재고 조회 로직을 실행한다.
	 * @param productUuid
	 * @return
	 */
	public int getInventory(String productUuid) {
		int productId = getProductIdFromUuid(productUuid);

		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new EntityNotFoundException("Product not found with UUID: " + productUuid));

		return product.getRemainQuantity();
	}

	/**
	 * @description : 재고 업데이트 로직
	 * @param productUuid
	 * @param quantity
	 * @return
	 */
	@Transactional
	public void updateInventory(String productUuid, int quantity) {

		// productUuid가 아닌 내부키 productId조회
		int productId = getProductIdFromUuid(productUuid);

		// mysql 원 디비에서 product 조회 - PESSIMISTIC_LOCK을 사용하여, 데이터를 읽거나 수정할 때 다른 트랜잭션이 해당 데이터를 동시에 수정하지 못하도록 함
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new EntityNotFoundException("Product not found with UUID:" + productUuid));

		// newStock = 남은재고 + 추가 / 감소할 재고
		int newStock = product.getRemainQuantity() + quantity;

		if (newStock < 0) {
			throw new InventoryShortageException();
		}

		product.setRemainQuantity(newStock);

	}

	/**
	 * @description : 외부키인 uuid를 받아서, 상품의 내부키 productId를 조회하는 메서드
	 * @param productUuid
	 * @return
	 */
	public Integer getProductIdFromUuid(String productUuid) {
		return productRepository.findByProductUuid(productUuid)
			.map(Product::getId)
			.orElseThrow(() -> new ProductEntityNotFoundException(productUuid));
	}
}
