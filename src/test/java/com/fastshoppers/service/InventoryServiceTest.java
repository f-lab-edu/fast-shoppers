package com.fastshoppers.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import com.fastshoppers.entity.Product;
import com.fastshoppers.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {

	@Mock
	private RedissonClient redissonClient;

	@Mock
	private InventoryRedisService inventoryRedisService;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private RLock lock;

	@Captor
	private ArgumentCaptor<Product> productArgumentCaptor;

	@InjectMocks
	private InventoryService inventoryService;

	@BeforeEach
	void setUp() {
		productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
	}

	@Test
	public void whenGetInventory_thenCorrectInventoryReturned() {
		// given
		String productUuid = "test-uuid";
		int expectedInventory = 10;

		Product mockProduct = new Product();
		mockProduct.setId(1);

		when(redissonClient.getLock(anyString())).thenReturn(lock);
		when(inventoryRedisService.getInventory(anyInt())).thenReturn(expectedInventory);
		when(productRepository.findByProductUuid(productUuid)).thenReturn(Optional.of(mockProduct));

		// when
		int actualInventory = inventoryService.getInventory(productUuid);

		// then
		assertEquals(expectedInventory, actualInventory);
	}

	@Test
	public void whenUpdateInventory_withValidData_thenSucceed() {
		// given
		String productUuid = "test-uuid";
		int productId = 1;
		int quantityToDecrease = -5;
		int currentInventory = 10;
		int newInventory = currentInventory + quantityToDecrease;

		Product product = new Product();
		product.setId(productId);
		product.setRemainQuantity(currentInventory);

		when(redissonClient.getLock(anyString())).thenReturn(lock);
		when(productRepository.findByProductUuid(productUuid)).thenReturn(Optional.of(product));
		when(productRepository.findById(productId)).thenReturn(Optional.of(product));
		doNothing().when(inventoryRedisService).updateInventory(anyInt(), anyInt());

		inventoryService.updateInventory(productUuid, quantityToDecrease);

		// then
		verify(inventoryRedisService).updateInventory(productId, newInventory);
		verify(productRepository).save(productArgumentCaptor.capture());
		assertEquals(newInventory, productArgumentCaptor.getValue().getRemainQuantity());
	}

	@Test
	public void whenConcurrentUpdateInventory_thenSucceed() throws Exception {
		// given
		String productUuid = "test-uuid";
		int productId = 1;
		int currentInventory = 100;
		int numberOfThreads = 10;
		int quantityToDecreasePerThread = -1;
		// CountDownLatch : 동시성 프로그래밍에서 사용되는 동기화 도구로, 하나 이상의 스레드 작업의 완료를 기다릴때 사용. 생성자에서 카운트 수를 받아 초기화함.
		CountDownLatch latch = new CountDownLatch(numberOfThreads);
		// 스레드 풀을 관리하고, 비동기적으로 태스크 실행할 수 있게해주는 서비스.
		ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);

		Product product = new Product();
		product.setId(productId);
		product.setRemainQuantity(currentInventory);

		when(redissonClient.getLock(anyString())).thenReturn(lock);
		when(productRepository.findByProductUuid(productUuid)).thenReturn(Optional.of(product));
		when(productRepository.findById(productId)).thenReturn(Optional.of(product));

		// when
		for (int i = 0; i < numberOfThreads; i++) {
			service.execute(() -> {
				try {
					inventoryService.updateInventory(productUuid, quantityToDecreasePerThread);
				} finally {
					latch.countDown(); // 하나씩 줄어들음.
				}
			});
		}

		latch.await(); // 0개가 되면 메인 테스트 스레드 해제됨.

		// then
		int expectedFinalInventory = currentInventory + (numberOfThreads * quantityToDecreasePerThread);
		verify(inventoryRedisService, times(numberOfThreads)).updateInventory(eq(productId), anyInt());
		verify(productRepository, times(numberOfThreads)).save(productArgumentCaptor.capture());

		Product capturedProduct = productArgumentCaptor.getValue();
		assertEquals(expectedFinalInventory, capturedProduct.getRemainQuantity());

		service.shutdown();

	}

}
