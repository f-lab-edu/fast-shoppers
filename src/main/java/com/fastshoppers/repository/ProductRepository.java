package com.fastshoppers.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import com.fastshoppers.entity.Product;

import jakarta.persistence.LockModeType;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	Optional<Product> findByProductUuid(String productUuid);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<Product> findById(Integer productId);

}
