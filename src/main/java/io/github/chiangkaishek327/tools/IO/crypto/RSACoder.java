package io.github.chiangkaishek327.tools.IO.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSACoder implements Coder {

    Cipher cipher = Cipher.getInstance("RSA");
    Key key;
    int mode;
    int keylen;

    /**
     * 
     * @param keysize key size
     * @param salt    key salt
     * @return a new rsa key pair
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair generateKeyPair(int keysize, byte[] salt) throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(keysize, new SecureRandom(salt));
        return generator.generateKeyPair();
    }

    /**
     * 
     * @param keysize key size
     * @return a new rsa key pair
     * @throws NoSuchAlgorithmException
     */

    public static KeyPair generateKeyPair(int keysize) throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(keysize, new SecureRandom());
        return generator.generateKeyPair();
    }

    /**
     * 
     * @return a new rsa key pair
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        return generateKeyPair(2048);
    }

    /**
     * 
     * @param input encoded private key
     * @return parsed private key
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static Key toPrivateKey(byte[] input) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(input);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePrivate(keySpec);
    }

    /**
     * 
     * @param input encoded public key
     * @return parsed public key
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */

    public static Key toPublicKey(byte[] input) throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(input);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePublic(keySpec);
    }

    public static int getPrivateKeyLength(PrivateKey input) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory factory = KeyFactory.getInstance("RSA");
        RSAPrivateKeySpec spec = factory.getKeySpec(input, RSAPrivateKeySpec.class);
        return spec.getModulus().toString(2).length();
    }

    public static int getPublicKeyLength(PublicKey input) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory factory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec spec = factory.getKeySpec(input, RSAPublicKeySpec.class);
        return spec.getModulus().toString(2).length();
    }

    /**
     * 
     * @param opmode operation mode(Cipher.ENCRYPT_MODE or Cipher.ENCRYPT_MODE)
     * @param key    key
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * 
     */
    public RSACoder(int opmode, Key key)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        super();
        initialize(opmode, key);
    }

    public CipherOutputStream getCipherOutputStream(OutputStream outputStream) {
        return new CipherOutputStream(outputStream, cipher);
    }

    public CipherInputStream getCipherInputStream(InputStream inputStream) {
        return new CipherInputStream(inputStream, cipher);
    }

    public void moveCodedContent(InputStream inputStream, OutputStream outputStream)
            throws IOException, BadPaddingException, IllegalBlockSizeException {
        int outputlen = mode == Cipher.ENCRYPT_MODE ? keylen / 8 : (keylen / 8) - 11;
        int inputlen = mode == Cipher.ENCRYPT_MODE ? outputlen - 11 : outputlen + 11;
        for (; inputStream.available() > inputlen;) {
            outputStream.write(cipher.doFinal(inputStream.readNBytes(inputlen)));
        }
        if (inputStream.available() != 0) {
            outputStream.write(cipher.doFinal(inputStream.readAllBytes()));
        }
    }

    public void initialize(int opmode, Key key)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        this.key = key;
        this.mode = opmode;
        try {
            keylen = getPublicKeyLength((PublicKey) key);
        } catch (Exception e) {

            keylen = getPrivateKeyLength((PrivateKey) key);
        }
        cipher.init(opmode, key);
    }

    public Cipher getCipher() {
        return cipher;
    }

    public Key getKey() {
        return key;
    }

    @Override
    public byte[] code(byte[] input) throws BadPaddingException, IllegalBlockSizeException {
        return cipher.doFinal(input);
    }

}
