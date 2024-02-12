package com.fastshoppers.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fastshoppers.common.ResponseMessage;
import com.fastshoppers.common.annotation.AuthenticatedUser;
import com.fastshoppers.common.util.JwtUtil;
import com.fastshoppers.model.CouponCreateRequest;
import com.fastshoppers.model.CouponResponse;
import com.fastshoppers.service.CouponService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coupon")
public class CouponController {

	private final CouponService couponService;

	private final JwtUtil jwtUtil;

	/**
	 * @description : 쿠폰 재고를 조회한다.
	 * @param couponId
	 * @return ResponseMessage
	 */
	@GetMapping("/{couponId}")
	public ResponseMessage getCouponStock(@PathVariable int couponId) {
		long stock = couponService.getCouponStock(couponId);
		return ResponseMessage.ok(stock);
	}

	/**
	 * @description : 쿠폰 유효성을 체크하고, 멤버에게 발급한다.
	 * @param email
	 * @param couponId
	 * @return ResponseMessage
	 */
	@PostMapping("/{couponId}")
	public ResponseMessage issueCouponToMember(@AuthenticatedUser String email,
		@PathVariable int couponId) {

		CouponResponse couponResponse = couponService.issueCouponToMember(couponId, email);

		return ResponseMessage.ok(couponResponse);
	}

	/**
	 * @description : 테스트를 수월하게 하기 위한 API로, 쿠폰 발급에 필요한 쿠폰을 새로 생성한다.
	 * @param couponCreateRequest
	 * @return ResponseMessage
	 */
	@PostMapping("/create")
	public ResponseMessage createCoupon(@RequestBody CouponCreateRequest couponCreateRequest) {
		couponService.createCoupon(couponCreateRequest);

		return ResponseMessage.ok();
	}

}
