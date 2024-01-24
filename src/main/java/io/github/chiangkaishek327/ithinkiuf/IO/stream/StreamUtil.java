package io.github.chiangkaishek327.ithinkiuf.IO.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Consumer;

public class StreamUtil {
    /**
     * Until I find the method "transferTo" of {@link java.io.InputStream}...
     * 
     * @param inputStream  target inputstream contains all contents need move
     * @param outputStream target outputstream need contents
     * @param blocksize    buffer size (set its size bigger for faster speed)
     * @throws IOException
     */
    @Deprecated
    public static void moveContent(InputStream inputStream, OutputStream outputStream, int blocksize)
            throws IOException {
        for (; inputStream.available() >= blocksize;) {
            outputStream.write(inputStream.readNBytes(blocksize));
        }
        outputStream.write(inputStream.readAllBytes());
        outputStream.flush();
    }

    /**
     * 
     * @param inputStream  target inputstream contains all contents need move
     * @param outputStream target outputstream need contents
     * @param blocksize    buffer size (set its size bigger for faster speed)
     * @param doonmove     do this when moving content (accept block size of moving)
     * @throws IOException
     */
    public static void moveContent(InputStream inputStream, OutputStream outputStream, int blocksize,
            Consumer<Integer> doonmove)
            throws IOException {
        for (; inputStream.available() >= blocksize;) {
            outputStream.write(inputStream.readNBytes(blocksize));
            doonmove.accept(blocksize);
        }
        doonmove.accept(inputStream.available());
        outputStream.write(inputStream.readAllBytes());
        outputStream.flush();
    }
}
