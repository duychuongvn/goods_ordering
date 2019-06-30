package com.github.duychuongvn.core.otp;

import com.github.duychuongvn.core.util.DateTimeUtil;
import com.github.duychuongvn.exception.TechnicalException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

/**
 * Created by huynhduychuong on Oct 2, 2016.
 */
public class OTPGeneratorUtil {
    private static final int TRUNCATION_OFFSET = 3;

    /**
     * Generate OTP base minute
     *
     * @param secret Secret Code
     * @param length OTP Length
     * @return OTP Value
     * @throws OTPGenerationException
     */
    public static String generateOTP(String secret, int length) throws OTPGenerationException {
        String interval = DateTimeUtil.convertDateToString(Calendar.getInstance().getTime(), "yyyyMMddHHmm");
        return generateOTP(secret, Long.valueOf(interval), length);

    }

    public static String generateOTP8Digits(String secret) {
        try {
            return generateOTP(secret, Calendar.getInstance().getTimeInMillis(),8);
        } catch (OTPGenerationException e) {
            throw new TechnicalException(e);
        }
    }

    /**
     * Generate OTP base interval
     *
     * @param secret   Secret Code
     * @param interval Interval
     * @param length   OTP Length
     * @return OTP Value
     * @throws OTPGenerationException
     */
    public static String generateOTP(String secret, long interval, int length) throws OTPGenerationException {
        try {
            return HOTPAlgorithm.generateOTP(secret.getBytes(), interval, length, true, TRUNCATION_OFFSET);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            throw new OTPGenerationException(e);
        }
    }
}
