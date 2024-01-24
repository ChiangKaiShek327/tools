package io.github.chiangkaishek327.ithinkiuf.IO.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import io.github.chiangkaishek327.ithinkiuf.IO.FileUtil;
import io.github.chiangkaishek327.ithinkiuf.IO.stream.StreamUtil;

public class ZipCompressor<K> extends Zipper<K> {
    /**
     * 
     * @param cacheSize cache size
     */
    public ZipCompressor(int cacheSize) {
        setCacheSize(cacheSize);
    }

    public ZipCompressor() {
        setCacheSize(131072);
    }

    /**
     * 
     * @param taskkey
     * @param zipOutputStream target {@link java.util.zip.ZipOutputStream}
     * @param targetFile      file need to be compressed
     * @throws IOException
     */
    public CompressTask compress(K taskkey, ZipOutputStream zipOutputStream, File targetFile) throws IOException {
        return new CompressTask(taskkey, zipOutputStream, targetFile);

    }

    public class CompressTask extends ZipperTask {
        private final File targetFile;

        private final ZipOutputStream zipOutputStream;
        private final long totalsize;
        private long compressed;

        /**
         * 
         * @param taskkey
         * @param zipOutputStream target {@link java.util.zip.ZipOutputStream}
         * @param targetFile      file need to be compressed
         * @throws IOException
         */
        protected CompressTask(K taskkey, ZipOutputStream zipOutputStream, File targetFile) throws IOException {
            super(taskkey);
            this.targetFile = targetFile;
            this.zipOutputStream = zipOutputStream;
            if (targetFile.isFile()) {
                totalsize = targetFile.length();
            } else {
                totalsize = FileUtil.getDirectorySize(targetFile);
            }
        }

        public void run() throws IOException {
            ensureOpen();
            FileInputStream fileInputStream;
            if (targetFile.isFile()) {
                zipOutputStream.putNextEntry(new ZipEntry(targetFile.getName()));
                fileInputStream = new FileInputStream(targetFile);
                StreamUtil.moveContent(fileInputStream, zipOutputStream, cacheSize,
                        e -> {
                            compressed = +e;
                            progress.setValue((double) compressed / (double) totalsize);
                        });
            } else {
                List<String> files = FileUtil.getFileRelativePathList(targetFile);
                for (String fileRelativePath : files) {
                    File file = new File(targetFile + "\\" + fileRelativePath);
                    System.out.println(fileRelativePath);
                    if (file.isDirectory()) {

                        zipOutputStream.putNextEntry(new ZipEntry(fileRelativePath + "/"));
                    } else {
                        zipOutputStream.putNextEntry(new ZipEntry(fileRelativePath));
                        fileInputStream = new FileInputStream(file);
                        StreamUtil.moveContent(fileInputStream, zipOutputStream, cacheSize,
                                e -> {
                                    compressed = +e;
                                    progress.setValue((double) compressed / (double) totalsize);
                                });
                    }
                }
            }
            zipOutputStream.flush();
            zipOutputStream.finish();
            close();
        }

        @Override
        public void close() throws IOException {
            zipOutputStream.close();
            taskMap.remove(getKey());
        }
    }
}
