package io.github.chiangkaishek327.ithinkiuf.IO.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HASHutil {
    public static final String SHA256 = "sha-256", MD5 = "MD5", SHA1 = "sha-1", SHA384 = "sha-384", SHA512 = "sha-512",
            MD2 = "MD2";

    /**
     * 
     * @param input bytes need convert
     * @return hex string
     */
    public static String ToHexStr(byte[] input) {
        StringBuilder sb = new StringBuilder();
        for (byte b : input) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * 
     * @param inputStream inputstream contains all content
     * @param type        hash type
     * @return hashcode of contents
     * 
     */
    public static String GetHash(InputStream inputStream, String type) throws NoSuchAlgorithmException, IOException {
        MessageDigest mg = MessageDigest.getInstance(type);
        byte[] buffer = new byte[4096];
        for (; inputStream.available() >= buffer.length;) {
            inputStream.read(buffer);
            mg.update(buffer);
        }
        mg.update(inputStream.readAllBytes());
        return ToHexStr(mg.digest());
    }
}
