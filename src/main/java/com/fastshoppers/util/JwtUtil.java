package com.fastshoppers.util;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${token.access.expiry.milliseconds}")
	private long accessTokenExpiryMilliSeconds;

	@Value("${token.refresh.expiry.milliseconds}")
	private long refreshTokenExpiryMilliSeconds;

	/**
	 * description : 1시간의 만료기간이 있는 토큰을 생성해서 리턴
	 * @param claimsMap
	 * @return Jwt accessToken
	 */
	public String generateAccessToken(Map<String, Object> claimsMap) {
		Claims claims = Jwts.claims(claimsMap);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiryMilliSeconds))
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}

	/**
	 * @description : 일주일의 만료기간이 있는 refreshToken 생성
	 * @param claimsMap
	 * @return JWT refreshToken
	 */
	public String generateRefreshToken(Map<String, Object> claimsMap) {
		Claims claims = Jwts.claims(claimsMap);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiryMilliSeconds))
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}

	/**
	 * @description : 토큰의 유효기간을 반환한다.
	 * @param token
	 * @return
	 */
	public Date getExpirationFromToken(String token) {
		Claims claims = getAllClaimsFromToken(token);

		return claims.getExpiration();
	}

	/**
	 * @description : 모든 정보를 반환한다.
	 * @param token
	 * @return
	 */
	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}

	/**
	 * @description : token이 expired되었는지 확인한다.
	 * @param token
	 * @return boolean val
	 */
	public boolean isTokenExpired(String token) {
		Date expiration = getExpirationFromToken(token);
		return expiration.before(new Date());
	}

}
