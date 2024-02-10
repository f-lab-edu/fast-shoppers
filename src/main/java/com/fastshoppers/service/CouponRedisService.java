package com.fastshoppers.service;

import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fastshoppers.common.exception.CouponNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponRedisService {

	private final RedissonClient redissonClient;

	@Value("${coupon.stock.key}")
	private String couponStockKey;

	@Value("${coupon.lock.key}")
	private String couponLockKey;

	/**
	 * @param couponId
	 * @return
	 * @description : redis 디비의 쿠폰 재고 조회 작업
	 */
	public long checkCouponStock(int couponId) {
		String key = couponStockKey + couponId;
		Long stockValue = redissonClient.getAtomicLong(key).get();

		if (stockValue != null) {
			return stockValue;
		} else {
			throw new CouponNotFoundException();
		}
	}

	/**
	 * @description : redis 디비의 coupon 개수 업데이트 작업
	 * @param couponId
	 * @return
	 */
	public boolean issueCoupon(int couponId) {
		RAtomicLong stockCount = redissonClient.getAtomicLong(couponStockKey + couponId);
		RLock lock = redissonClient.getLock(couponLockKey + couponId);
		lock.lock();
		try {
			long stock = stockCount.get();
			if (stock > 0) {
				return stockCount.decrementAndGet() >= 0;
			}
			return false;
		} finally {
			lock.unlock();
		}
	}

}
