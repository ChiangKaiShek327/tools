package io.github.chiangkaishek327.ithinkiuf.IO.stream;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import io.github.chiangkaishek327.ithinkiuf.utils.NumberUtil;

public class WriterPP {
    OutputStream thisOutputStream;

    public WriterPP(OutputStream arg0) throws Exception {
        thisOutputStream = arg0;
    }

    public void writeString(String arg0, Charset arg1) throws IOException {
        writeByteArray(arg0.getBytes(arg1));
    }

    public void writeString(String arg0) throws IOException {
        writeString(arg0, StandardCharsets.UTF_8);
    }

    public void writeInt(int arg0) throws IOException {
        thisOutputStream.write(NumberUtil.intToByteArray(arg0));
    }

    public void writeLong(long arg0) throws IOException {
        thisOutputStream.write(NumberUtil.longToByteArray(arg0));
    }

    public void writeBoolean(boolean arg0) throws IOException {
        writeByte(arg0 ? 1 : 0);
    }

    public void writeByteArray(byte[] arg0) throws IOException {
        writeInt(arg0.length);
        thisOutputStream.write(arg0);
    }

    public void writeByte(int arg0) throws IOException {
        thisOutputStream.write(arg0);
    }

    public void close() throws IOException {
        thisOutputStream.close();
    }
}
