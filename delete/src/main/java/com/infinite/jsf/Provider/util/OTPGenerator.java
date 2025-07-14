package com.infinite.jsf.Provider.util;

import java.security.SecureRandom;

public class OTPGenerator {
	private static final String DIGITS = "0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Generates a numeric OTP of the given length.
     *
     * @param length the number of digits in the OTP (must be > 0)
     * @return a random numeric OTP as a String
     * @throws IllegalArgumentException if length <= 0
     */
    public static String generateOTP(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("OTP length must be positive");
        }

        StringBuilder otp = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int idx = RANDOM.nextInt(DIGITS.length());
            otp.append(DIGITS.charAt(idx));
        }
        return otp.toString();
    }

}
