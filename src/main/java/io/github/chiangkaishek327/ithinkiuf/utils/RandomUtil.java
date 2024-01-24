package io.github.chiangkaishek327.ithinkiuf.utils;

import java.util.Random;
import java.util.UUID;

public class RandomUtil {
    public static String createGUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 
     * @param length length of bytearray
     * @return a random bytearray
     */
    public static byte[] randomByteArray(int length) {
        byte[] result = new byte[length];
        new Random().nextBytes(result);
        return result;
    }

    public static String randomString(int length, String chars) {
        String crs = chars + " ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(crs.charAt((int) (Math.random() * (crs.length() - 1))));
        }
        return sb.toString();
    }

}
