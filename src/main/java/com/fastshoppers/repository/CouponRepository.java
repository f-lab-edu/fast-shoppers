package com.fastshoppers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fastshoppers.entity.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
}
