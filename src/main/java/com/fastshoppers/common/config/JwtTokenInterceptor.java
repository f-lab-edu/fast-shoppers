package com.fastshoppers.common.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fastshoppers.common.exception.InvalidTokenException;
import com.fastshoppers.common.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtTokenInterceptor implements HandlerInterceptor {

	private final JwtUtil jwtUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {

		String authorizationHeader = request.getHeader("Authorization");

		// Authorization 헤더가 없는 경우 건너 뛰기
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			return true;
		}

		// 요청 헤더에서 토큰 추출
		String token = authorizationHeader.replace("Bearer ", "");

		// 토큰의 유효성 검증
		if (jwtUtil.isTokenExpired(token)) {
			throw new InvalidTokenException();
		}

		// 토큰 사용자 정보 추출
		String email = jwtUtil.getAllClaimsFromToken(token).get("email", String.class);
		// 요청 attribute로 사용자 정보 저장
		request.setAttribute("email", email);
		// 다음 인터셉터, 컨트롤러로 진행
		return true;
	}
}
