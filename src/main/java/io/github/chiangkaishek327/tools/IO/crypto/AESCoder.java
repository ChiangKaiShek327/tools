package io.github.chiangkaishek327.tools.IO.crypto;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AESCoder implements Coder {
    Cipher cipher = Cipher.getInstance("AES");
    Key key;

    public static SecretKeySpec getKeySpec(byte[] key) {
        return new SecretKeySpec(key, "AES");
    }

    /**
     * 
     * @return a new aes key
     * @throws NoSuchAlgorithmException
     */
    public static Key generateAESkey() throws NoSuchAlgorithmException {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(new SecureRandom());
        return generator.generateKey();
    }

    /**
     * 
     * @param salt key salt
     * @return a new aes key
     * @throws NoSuchAlgorithmException
     */
    public static Key generateAESkey(byte[] salt) throws NoSuchAlgorithmException {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(new SecureRandom(salt));
        return generator.generateKey();
    }

    /**
     * 
     * @param salt key salt
     * @param size key size
     * @return a new aes key
     * @throws NoSuchAlgorithmException
     */
    public static Key generateAESkey(byte[] salt, int size) throws NoSuchAlgorithmException {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(size, new SecureRandom(salt));
        return generator.generateKey();
    }

    public static Key toAESkey(byte[] input) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return new SecretKeySpec(input, "AES");

    }

    /**
     * 
     * @param opmode operation mode(Cipher.ENCRYPT_MODE or Cipher.ENCRYPT_MODE)
     * @param key    key
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public AESCoder(int opmode, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        this.key = key;
        cipher.init(opmode, key);
    }

    /**
     * @param opmode operation mode(Cipher.ENCRYPT_MODE or Cipher.ENCRYPT_MODE)
     * @param key    key
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public void initialize(int opmode, Key key)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        this.key = key;
        cipher.init(opmode, key);
    }

    public Cipher getCipher() {
        return cipher;
    }

    public Key getKey() {
        return key;
    }

    public CipherOutputStream getCipherOutputStream(OutputStream outputStream) {
        return new CipherOutputStream(outputStream, cipher);
    }

    public CipherInputStream getCipherInputStream(InputStream inputStream) {
        return new CipherInputStream(inputStream, cipher);
    }

    @Override
    public byte[] code(byte[] input) throws BadPaddingException, IllegalBlockSizeException {
        return cipher.doFinal(input);
    }
}
