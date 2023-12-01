package com.fastshoppers.util;

import com.fastshoppers.exception.MemberNotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;


    /**
     * description : 1시간의 만료기간이 있는 토큰을 생성해서 리턴
     * @param email
     * @return Jwt accessToken
     */
    public String generateAccessToken(String email) {
        Claims claims = Jwts.claims();
        claims.put("email", email);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 ))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * @description : 일주일의 만료기간이 있는 refreshToken 생성
     * @param email
     * @return JWT refreshToken
     */
    public String generateRefreshToken(String email) {
        Claims claims = Jwts.claims();
        claims.put("email", email);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * @description : 토큰에 저장된 이메일 정보 반환
     * @param token
     * @return email value
     */
    public String getEmailFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);

        if(claims.get("email") == null) {
            throw new MemberNotFoundException();
        }
        return claims.get("email", String.class);
    }

    /**
     * @description : 토큰의 유효기간을 반환한다.
     * @param token
     * @return
     */
    public Date getExpirationFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);

        return claims.get("expiration", Date.class);
    }

    /**
     * @description : 유효한 토큰인지 확인한다.
     * @param token
     * @param email
     * @return
     */
    public boolean validateToken(String token, String email) {
        String emailFromToken = getEmailFromToken(token);
        return (emailFromToken.equals(email) && !isTokenExpired(token));
    }

    /**
     * @description : 모든 정보를 반환한다.
     * @param token
     * @return
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    /**
     * @description : token이 expired되었는지 확인한다.
     * @param token
     * @return boolean val
     */
    private boolean isTokenExpired(String token) {
        Date expiration = getExpirationFromToken(token);
        return expiration.before(new Date());
    }

}
