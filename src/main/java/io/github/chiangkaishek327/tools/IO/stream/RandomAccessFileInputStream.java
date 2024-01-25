package io.github.chiangkaishek327.tools.IO.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

public class RandomAccessFileInputStream extends InputStream {
    final RandomAccessFile randomAccessFile;

    public RandomAccessFileInputStream(RandomAccessFile raf) {
        randomAccessFile = raf;
    }

    public void seek(long position) throws IOException {
        randomAccessFile.seek(position);
    }

    @Override
    public int read() throws IOException {
        return randomAccessFile.read();
    }

    public void close() throws IOException {
        randomAccessFile.close();
    }

    public long getRemaining() throws IOException {
        return randomAccessFile.length() - 1 - randomAccessFile.getFilePointer();
    }

    @Override
    public int available() throws IOException {
        return (int) (getRemaining() > Integer.MAX_VALUE ? Integer.MAX_VALUE : getRemaining());
    }

    public RandomAccessFile getRandomAccessFile() {
        return randomAccessFile;
    }

}