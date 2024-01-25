package io.github.chiangkaishek327.tools.IO.crypto;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class Base64Util {
    private static Decoder decoder = Base64.getDecoder();
    private static Encoder encoder = Base64.getEncoder();

    public static String encode(byte[] data) {
        return encoder.encodeToString(data);
    }

    public static byte[] decode(String data) {
        return decoder.decode(data);
    }
}
