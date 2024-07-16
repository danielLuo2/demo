package com.ebay.demo.util;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
/**
 * @Author xudong luo
 * @Date 2024/7/15
 * @Description //TODO
 */
public class Base64Util {

    public static String encode(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }
        byte[] encodedBytes = Base64.getEncoder().encode(str.getBytes(StandardCharsets.UTF_8));
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }

    public static String decode(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(str);
            return new String(decodedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "";
        }
    }
}
