package com.fastshoppers.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fastshoppers.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	Optional<Product> findByProductUuid(String productUuid);
}
