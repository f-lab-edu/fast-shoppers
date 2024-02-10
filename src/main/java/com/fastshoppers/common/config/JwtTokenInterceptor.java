package com.fastshoppers.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fastshoppers.common.exception.InvalidTokenException;
import com.fastshoppers.common.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenInterceptor implements HandlerInterceptor {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {

		// 요청 헤더에서 토큰 추출
		String token = request.getHeader("Authorization").replace("Bearer ", "");

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
