package io.github.chiangkaishek327.tools.utils;

import java.nio.ByteBuffer;

public class NumberUtil {
    /**
     * 
     * @param i target number
     * @return bytes of number
     */
    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        for (int j = 0; j < result.length; j++) {
            result[j] = (byte) ((i >> (24 - (j * 8))) & 0xFF);
        }

        return result;
    }

    /**
     * 
     * @param bytes target bytes
     * @return parsed
     */
    public static int byteArrayToInt(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }

    /**
     * 
     * @param i target number
     * @return bytes of number
     */
    public static byte[] longToByteArray(long i) {
        byte[] result = new byte[8];
        for (int j = 0; j < result.length; j++) {
            result[j] = (byte) ((i >> (56 - (j * 8))) & 0xFF);
        }

        return result;
    }

    /**
     * 
     * @param bytes target bytes
     * @return parsed
     */
    public static long byteArrayToLong(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getLong();
    }
}
