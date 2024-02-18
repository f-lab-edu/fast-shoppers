package com.fastshoppers.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fastshoppers.entity.Coupon;
import com.fastshoppers.entity.Member;
import com.fastshoppers.model.CouponResponse;
import com.fastshoppers.repository.CouponHistoryRepository;
import com.fastshoppers.repository.CouponRepository;
import com.fastshoppers.repository.MemberRepository;

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

	@Test
	void whenIssueCouponToMember_thenIssueCoupon() {
		int couponId = 1;
		String email = "test@google.com";

		Member mockMember = new Member();
		mockMember.setEmail(email);
		Coupon mockCoupon = new Coupon();
		mockCoupon.setRemainingQuantity(10);
		mockCoupon.setTotalQuantity(10);

		given(memberRepository.findByEmail(email)).willReturn(mockMember);
		given(couponRepository.findById(couponId)).willReturn(Optional.of(mockCoupon));
		given(couponRedisService.issueCoupon(couponId)).willReturn(true);

		CouponResponse couponResponse = couponService.issueCouponToMember(couponId, email);

		assertNotNull(couponResponse);
		assertEquals(9, couponResponse.getRemainingStock());


		then(couponRepository).should(times(1)).save(any(Coupon.class));
		then(couponRedisService).should(times(1)).issueCoupon(couponId);

	}



}

