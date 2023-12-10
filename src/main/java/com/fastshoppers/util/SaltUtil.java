package com.fastshoppers.util;

import java.security.SecureRandom;

/**
 * @description : salt값을 생성하는 Util 클래스
 */
public class SaltUtil {

	/**
	 * @description : salt값을 생성하는 메서드
	 * @return salt값
	 */
	public static String generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16]; // 16바이트 솔트
		random.nextBytes(salt);
		return bytesToHex(salt);
	}

	/**
	 * @description : byte배열을 16진수 형태의 문자열로 변환하는 메서드
	 * @param bytes
	 * @return
	 */
	private static String bytesToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

}
