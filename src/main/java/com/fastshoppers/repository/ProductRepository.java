package com.fastshoppers.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fastshoppers.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	Optional<Product> findByProductUuid(String productUuid);
}
