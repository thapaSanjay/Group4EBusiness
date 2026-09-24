package com.group4.ebusiness.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class providing secure password hashing for the application.
 */
public class PasswordUtil {

    /**
    * Generates a SHA-512 hexadecimal hash of a plain-text password.
    *
    * @param password plain-text password entered by the user
    * @return SHA-512 hexadecimal password hash
    */
    public static String hashPassword(String password) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");

            byte[] encodedHash = digest.digest(
                    password.getBytes(StandardCharsets.UTF_8)
            );

            StringBuilder hexString = new StringBuilder();

            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);

                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-512 algorithm is not available.", e);
        }
    }
}