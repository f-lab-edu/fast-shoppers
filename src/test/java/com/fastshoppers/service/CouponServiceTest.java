package com.fastshoppers.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.fastshoppers.repository.CouponHistoryRepository;
import com.fastshoppers.repository.CouponRepository;
import com.fastshoppers.repository.MemberRepository;

@SpringBootTest
public class CouponServiceTest {

	@Mock
	private CouponRepository couponRepository;

	@Mock
	private CouponHistoryRepository couponHistoryRepository;

	@Mock
	private CouponRedisService couponRedisService;

	@Mock
	private MemberRepository memberRepository;

	@InjectMocks
	private CouponService couponService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void whenGetCouponStock_thenReturnStock() {
		int couponId = 1;
		long expectedStock = 10;

		when(couponService.getCouponStock(couponId)).thenReturn(expectedStock);

		long stock = couponService.getCouponStock(couponId);

		assertEquals(expectedStock, stock);
	}

}
