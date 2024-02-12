package com.fastshoppers.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CouponCreateRequest {
	// 쿠폰 명
	private String couponName;
	// 유효기간 startDate
	@Builder.Default
	private LocalDateTime validStartDateAt = LocalDateTime.now();
	// 유효기간 endDate
	@Builder.Default
	private LocalDateTime validEndDateAt = LocalDateTime.of(2030, 12, 31, 23, 59, 59);
	// 할인 타입
	private String discountType;
	// 할인 값
	private Integer discountValue;
	// 총 수량
	@Builder.Default
	private Integer totalQuantity = 100;
	// 수령 제한 수량
	@Builder.Default
	private Integer maxRedemption = 1;
	// 이벤트 시작일자
	private LocalDateTime eventStartDate;
	// 이벤트 종료일자
	private LocalDateTime eventEndDate;

}
