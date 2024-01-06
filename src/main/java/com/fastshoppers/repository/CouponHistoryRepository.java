package com.fastshoppers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fastshoppers.entity.CouponHistory;

@Repository
public interface CouponHistoryRepository extends JpaRepository<CouponHistory, Integer> {
	CouponHistory findByCouponIdAndMemberEmailAndDeleteYn(int couponId, String email, boolean deleteYn);
}
