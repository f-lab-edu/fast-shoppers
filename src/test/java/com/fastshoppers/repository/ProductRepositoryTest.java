package com.fastshoppers.repository;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fastshoppers.entity.Product;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTest {

	@Mock
	private ProductRepository productRepository;

	@BeforeEach
	void setUp() {
		Product product = new Product();
		product.setId(1);
		product.setProductUuid("test-uuid");
		when(productRepository.findByProductUuid("test-uuid")).thenReturn(Optional.of(product));
	}

	@Test
	void whenFindByProductUuid_thenReturnProduct() {
		// given
		String uuid = "test-uuid";

		Optional<Product> found = productRepository.findByProductUuid(uuid);

		assertThat(found).isPresent();
		assertThat(found.get().getProductUuid()).isEqualTo(uuid);
	}
}
