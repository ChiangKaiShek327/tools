package io.github.chiangkaishek327.ithinkiuf.IO.stream;

import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

public class RandomAccessFileOutputStream extends OutputStream {
    final RandomAccessFile randomAccessFile;

    public RandomAccessFileOutputStream(RandomAccessFile raf) {
        randomAccessFile = raf;
    }

    public void seek(long position) throws IOException {
        randomAccessFile.seek(position);
    }

    public void close() throws IOException {
        randomAccessFile.close();
    }

    public RandomAccessFile getRandomAccessFile() {
        return randomAccessFile;
    }

    @Override
    public void write(int b) throws IOException {
        randomAccessFile.write(b);
    }

}