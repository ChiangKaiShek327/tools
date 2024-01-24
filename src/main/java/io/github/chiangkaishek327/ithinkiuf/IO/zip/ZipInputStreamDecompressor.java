package io.github.chiangkaishek327.ithinkiuf.IO.zip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import io.github.chiangkaishek327.ithinkiuf.IO.stream.StreamUtil;

/**
 * cause by {@link java.util.zip.ZipInputStream},I can't get progress
 */
public class ZipInputStreamDecompressor<K> extends Zipper<K> {
    String savePath;

    /**
     * 
     * @param cacheSize cache size
     * @param savePath  the location where file decompressed storage in
     */
    public ZipInputStreamDecompressor(int cacheSize, String savePath) {
        setCacheSize(cacheSize);
        this.savePath = savePath;
    }

    public ZipInputStreamDecompressor(String savePath) {
        setCacheSize(131072);
        this.savePath = savePath;
    }

    public class ZipInputStreamDecompressTask extends ZipperTask {
        private final ZipInputStream zipInputStream;

        /**
         * 
         * @param key
         * @param zipInputStream target {@link java.util.zip.ZipInputStream }
         */
        public ZipInputStreamDecompressTask(K key, ZipInputStream zipInputStream) {
            super(key);
            this.zipInputStream = zipInputStream;

        }

        @Override
        public void close() throws IOException {
            ensureOpen();
            zipInputStream.close();
            taskMap.remove(getKey());
        }

        @SuppressWarnings("all")
        @Override
        public void run() throws IOException {
            ensureOpen();
            for (ZipEntry ze = zipInputStream.getNextEntry(); ze != null; ze = zipInputStream.getNextEntry()) {
                FileOutputStream fos;
                File file = new File(savePath + "/" + ze);
                if (ze.isDirectory()) {
                    file.mkdir();
                } else {
                    fos = new FileOutputStream(file);
                    fos.flush();
                    StreamUtil.moveContent(zipInputStream, fos, cacheSize);
                }
            }
            progress.setValue(1d);
            close();
        }
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
}
