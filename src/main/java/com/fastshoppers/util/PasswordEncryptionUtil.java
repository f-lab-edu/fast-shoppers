package com.fastshoppers.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptionUtil {

    /**
     * @description : SHA-256 암호화 알고리즘을 사용하여 패스워드 암호화
     * @// TODO: 2023-12-01  : 솔트 사용할 것인지. 사용하면 디비 컬럼에 추가해야함.  
     * @param password
     * @return
     */
    public static String encryptPassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = messageDigest.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();

            for(byte b : hashedPassword) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
