package com.fastshoppers.entity;

import java.time.LocalDateTime;

import com.fastshoppers.common.DiscountType;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "coupon_id", nullable = false))
@Table(name = "coupon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Coupon extends BaseEntity {

	@Column(name = "valid_start_date", nullable = false)
	private LocalDateTime validStartDate;

	@Column(name = "valid_end_date", nullable = false)
	private LocalDateTime validEndDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "discount_type", nullable = false)
	private DiscountType discountType;

	@Column(name = "discount_value", nullable = false)
	private Integer discountValue;

	@Column(name = "total_quantity", nullable = false)
	private Integer totalQuantity;

	@Column(name = "remaining_quantity", nullable = false)
	private Integer remainingQuantity;

	@Column(name = "max_redemption", nullable = false)
	@Builder.Default
	private Integer maxRedemption = 1;

	@Column(name = "event_start_date", nullable = true)
	private LocalDateTime eventStartDate;

	@Column(name = "event_end_date", nullable = true)
	private LocalDateTime eventEndDate;

	@Column(name = "coupon_name", nullable = true)
	private String couponName;

}
