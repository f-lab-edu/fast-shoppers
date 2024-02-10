package com.fastshoppers.service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fastshoppers.common.exception.DuplicateEmailException;
import com.fastshoppers.common.exception.InvalidPasswordException;
import com.fastshoppers.common.exception.LoginFailException;
import com.fastshoppers.common.exception.MemberNotFoundException;
import com.fastshoppers.common.util.JwtUtil;
import com.fastshoppers.common.util.PasswordEncryptionUtil;
import com.fastshoppers.common.util.SaltUtil;
import com.fastshoppers.entity.Member;
import com.fastshoppers.model.MemberRequest;
import com.fastshoppers.model.TokenResponse;
import com.fastshoppers.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	private final JwtUtil jwtUtil;

	private final AuthTokenRedisService authTokenRedisService;

	@Value("${token.refresh.expiry.milliseconds}")
	private int refreshTokenExpiryMilliSeconds;

	/**
	 * @description: 회원가입 비즈니스 로직. 이메일 중복 확인, password 유효성 검사 후 멤버 디비 저장
	 * @param memberRequest
	 * @return Member
	 */
	public Member registerMember(MemberRequest memberRequest) {
		if (memberRepository.findByEmail(memberRequest.getEmail()) != null) {
			throw new DuplicateEmailException();
		}

		if (!isValidPassword(memberRequest.getPassword())) {
			throw new InvalidPasswordException();
		}

		String salt = SaltUtil.generateSalt();

		Member member = convertToEntity(memberRequest, salt);
		member.setDeleteYn(false);

		return memberRepository.save(member);
	}

	public TokenResponse login(MemberRequest memberRequest) {
		Member member = memberRepository.findByEmail(memberRequest.getEmail());

		if (member == null) {
			throw new MemberNotFoundException();
		}

		String salt = member.getSalt();

		String hashedPassword = PasswordEncryptionUtil.encryptPassword(memberRequest.getPassword(), salt);

		if (!member.getPassword().equals(hashedPassword)) {
			throw new LoginFailException();
		}

		Map<String, Object> claims = new HashMap<>();
		claims.put("email", member.getEmail());

		String accessToken = jwtUtil.generateAccessToken(claims);
		String refreshToken = jwtUtil.generateRefreshToken(claims);

		authTokenRedisService.saveRefreshToken(member.getEmail(), refreshToken, refreshTokenExpiryMilliSeconds);

		return TokenResponse.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	/**
	 * @description: DTO -> Entity로 변환하는 메서드
	 * @param memberRequest
	 * @return Member
	 */
	private Member convertToEntity(MemberRequest memberRequest, String salt) {
		return Member.builder()
			.email(memberRequest.getEmail())
			.salt(salt)
			.password(PasswordEncryptionUtil.encryptPassword(memberRequest.getPassword(), salt))
			.build();
	}

	/**
	 * @description: 패스워드 유효성 체크. 대소문자, 특수문자 포함 8~16자리 체크
	 * @param password
	 * @return boolean value
	 */
	private boolean isValidPassword(String password) {
		String passwordPattern = "^[a-zA-Z0-9]{8,16}$";
		return Pattern.matches(passwordPattern, password);
	}

	/**
	 * @description : 로그아웃 메서드. redis에서 refreshToken을 삭제한다.
	 * @param memberRequest
	 */
	public void logout(MemberRequest memberRequest) {
		authTokenRedisService.deleteRefreshToken(memberRequest.getEmail());
	}

	/**
	 * @description : 이메일을 반환하는 메서드.
	 * @param email
	 * @return
	 */
	public Member getMemberByEmail(String email) {
		return memberRepository.findByEmail(email);
	}
}
