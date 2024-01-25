package io.github.chiangkaishek327.tools.IO.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;

public interface Coder {
    /**
     * 
     * @param input data uncoded
     * @return coded data
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public byte[] code(byte[] input) throws BadPaddingException, IllegalBlockSizeException;

    /**
     * 
     * @param target target inputstream contains uncoded data
     * @return cipherinputstream of target inputstream
     */
    public CipherInputStream getCipherInputStream(InputStream target);

    /**
     * 
     * @param target target outputstream need coded data
     * @return cipheroutputstream of target outputstream
     */
    public CipherOutputStream getCipherOutputStream(OutputStream target);

    /**
     * 
     * @param inputStream  target inputstream contains uncoded data
     * @param outputStream target outputstream need coded data
     * @throws IOException
     */
    public default void moveCodedContent(InputStream inputStream, OutputStream outputStream) throws Exception {
        inputStream.transferTo(outputStream);
    }

    public Cipher getCipher();
}
