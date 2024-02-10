package com.fastshoppers.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fastshoppers.common.CouponStatus;
import com.fastshoppers.entity.Coupon;
import com.fastshoppers.entity.CouponHistory;
import com.fastshoppers.entity.Member;
import com.fastshoppers.exception.CouponAlreadyIssuedException;
import com.fastshoppers.exception.CouponNotFoundException;
import com.fastshoppers.exception.CouponShortageException;
import com.fastshoppers.exception.MemberNotFoundException;
import com.fastshoppers.model.CouponResponse;
import com.fastshoppers.repository.CouponHistoryRepository;
import com.fastshoppers.repository.CouponRepository;
import com.fastshoppers.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CouponService {
	private final CouponRepository couponRepository;

	private final CouponHistoryRepository couponHistoryRepository;

	private final CouponRedisService couponRedisService;

	private final MemberRepository memberRepository;

	/**
	 * @description : 쿠폰 재고를 조회하는 메서드
	 * @param couponId
	 * @return
	 */
	public long getCouponStock(int couponId) {
		return couponRedisService.checkCouponStock(couponId);
	}

	/**
	 * @description : 쿠폰 재고를 차감하고, 멤버의 쿠폰 발급 내역을 업데이트하는 메서드
	 * @param couponId
	 * @param email
	 * @return
	 */
	@Transactional
	public CouponResponse issueCouponToMember(int couponId, String email) {
		validateCouponAvailabilty(couponId, email); // 쿠폰 발급 validation 체크 

		Member member = getMemberByEmail(email); // 이메일로 멤버 조회
		Coupon coupon = getCouponById(couponId); // 쿠폰 id로 쿠폰 조회

		decreaseCouponStock(coupon); // 쿠폰 감소 로직

		String couponUuid = createCouponHistory(member, coupon); // 쿠폰 발급 내역에 업데이트하고, 쿠폰 발급 외부 노출용 키 uuid받아옴

		return CouponResponse.builder()
			.uuid(couponUuid)
			.remainingStock(coupon.getRemainingQuantity())
			.build();
	}

	/**
	 * @description : 쿠폰 발급 유효성 체크
	 * @param couponId
	 * @param email
	 */
	private void validateCouponAvailabilty(int couponId, String email) {

		if (isCouponAlreadyIssued(couponId, email)) {
			throw new CouponAlreadyIssuedException();
		}

		if (!couponRedisService.issueCoupon(couponId)) { // redis에 업데이트
			throw new CouponShortageException();
		}

	}

	/**
	 * @description : 쿠폰 발급 여부 조회
	 * @param couponId
	 * @param email
	 * @return
	 */
	private boolean isCouponAlreadyIssued(int couponId, String email) {
		return couponHistoryRepository.findByCouponIdAndMemberEmailAndDeleteYn(couponId, email, false) != null;
	}

	/**
	 * @description : email로 회원 조회
	 * @param email
	 * @return
	 */
	private Member getMemberByEmail(String email) {
		Member member = memberRepository.findByEmail(email);

		if (member == null) {
			throw new MemberNotFoundException();
		}

		return member;
	}

	/**
	 * @description : RDB에서 쿠폰 가져오는 로직
	 * @param couponId
	 * @return
	 */
	private Coupon getCouponById(int couponId) {
		Coupon coupon = couponRepository.findById(couponId)
			.orElseThrow(() -> new CouponNotFoundException());
		return coupon;
	}

	/**
	 * @description : RDB coupon 재고 감소
	 * @param coupon
	 */
	private void decreaseCouponStock(Coupon coupon) {
		if (coupon.getTotalQuantity() <= 0 || coupon.getRemainingQuantity() <= 0) {
			throw new CouponShortageException();
		}

		coupon.setRemainingQuantity(coupon.getRemainingQuantity() - 1);
		couponRepository.save(coupon);
	}

	/**
	 * @description : RDB에 쿠폰 발급 내역을 생성한다.
	 * @param member
	 * @param coupon
	 * @return
	 */
	private String createCouponHistory(Member member, Coupon coupon) {
		String couponUuid = UUID.randomUUID().toString();
		CouponHistory couponHistory = CouponHistory.builder()
			.member(member)
			.coupon(coupon)
			.couponUuid(couponUuid)
			.issueDate(LocalDateTime.now())
			.couponStatus(CouponStatus.ACTIVE)
			.build();

		couponHistory.setDeleteYn(false);

		couponHistoryRepository.save(couponHistory);
		return couponUuid;
	}

}