package com.fastshoppers.entity;

import java.time.LocalDateTime;

import com.fastshoppers.common.CouponStatus;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "coupon_history_id", nullable = false))
@Table(name = "coupon_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CouponHistory extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "coupon_id", referencedColumnName = "coupon_id")
	private Coupon coupon;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mmbr_num", referencedColumnName = "mmbr_num")
	private Member member;

	@Column(name = "coupon_uuid", nullable = false)
	private String couponUuid;

	@Column(name = "issue_date", nullable = false)
	private LocalDateTime issueDate;

	@Column(name = "usage_date", nullable = true)
	private LocalDateTime usageDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "coupon_status", nullable = false)
	private CouponStatus couponStatus;
}
