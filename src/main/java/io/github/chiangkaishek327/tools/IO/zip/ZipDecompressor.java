package io.github.chiangkaishek327.tools.IO.zip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import io.github.chiangkaishek327.tools.IO.stream.StreamUtil;

public class ZipDecompressor<K> extends Zipper<K> {
    String savePath;

    /**
     * 
     * @param cacheSize cache size
     * @param savePath  the location where file decompressed storage in
     */
    public ZipDecompressor(int cacheSize, String savePath) {
        super(cacheSize);
        this.savePath = savePath;
    }

    /**
     * 
     * @param key
     * @param zipfile zip file need to be decompressed
     * @return
     */
    public DecompressTask decompress(K key, ZipFile zipfile) {
        return new DecompressTask(key, zipfile);
    }

    public class DecompressTask extends ZipperTask {
        private final ZipFile zipFile;
        private final List<ZipEntry> entries = new ArrayList<>();
        private final long totalsize;
        private long compressed;

        public DecompressTask(K key, ZipFile zipFile) {
            super(key);
            taskMap.put(key, this);
            this.zipFile = zipFile;
            Enumeration<? extends ZipEntry> entriesenum = this.zipFile.entries();
            long ts = 0;
            for (ZipEntry ze = entriesenum.nextElement(); entriesenum
                    .hasMoreElements(); ze = entriesenum.nextElement()) {
                entries.add(ze);
                ts = ts + ze.getSize();
            }
            totalsize = ts;

        }

        @Override
        public void run() throws IOException {
            ensureOpen();
            InputStream is;
            FileOutputStream fos;

            for (ZipEntry zipEntry : entries) {
                File file = new File(savePath + "/" + zipEntry);
                if (zipEntry.isDirectory()) {
                    file.mkdir();
                } else {
                    is = zipFile.getInputStream(zipEntry);
                    fos = new FileOutputStream(file);
                    StreamUtil.moveContent(is, fos, cacheSize,
                            e -> {
                                compressed = compressed + e;
                                progress.setValue((double) compressed / (double) totalsize);
                            });
                }
            }
        }

        @Override
        public void close() throws IOException {
            ensureOpen();
            zipFile.close();
            taskMap.remove(key);

        }

    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
}
