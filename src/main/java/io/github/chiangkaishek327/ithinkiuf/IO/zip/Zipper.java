package io.github.chiangkaishek327.ithinkiuf.IO.zip;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.zip.ZipException;

import io.github.chiangkaishek327.ithinkiuf.observable.ObservableMap;
import io.github.chiangkaishek327.ithinkiuf.observable.ObservableValue;

/**
 * strange name,isn't it?
 */
public class Zipper<K> {
    protected ObservableMap<K, ZipperTask> taskMap = new ObservableMap<>();
    protected int cacheSize;

    public Zipper(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    public Zipper() {
        this.cacheSize = 131072;
    }

    /**
     * 
     * @return collection contains all task, for iter
     */
    public Collection<? extends ZipperTask> getTasks() {
        return taskMap.values();

    }

    /**
     * 
     * @return cache size when moving content
     */
    public int getCacheSize() {
        return cacheSize;
    }

    /**
     * 
     * @param cacheSize cache size when moving content
     */
    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    /**
     * 
     * @return map contains all task
     */
    public ObservableMap<K, ZipperTask> getTaskMap() {
        return taskMap;
    }

    public abstract class ZipperTask implements Closeable {
        final K key;
        boolean open = true;

        public boolean isOpen() {
            return open;
        }

        protected void ensureOpen() throws ZipException {
            if (!open) {
                throw new ZipException("closed");
            }
        }

        /**
         * 
         * @param key task key for find it in taskmap
         */
        public ZipperTask(K key) {
            taskMap.put(key, this);
            this.key = key;
        }

        final ObservableValue<Double> progress = new ObservableValue<Double>(0d);

        /**
         * run task and close after
         * 
         * @throws IOException
         */
        public abstract void run() throws IOException;

        /**
         * 
         * @return its progress
         *         {@link io.github.chiangkaishek327.ithinkiuf.observable.ObservableValue}
         */
        public ObservableValue<Double> getProgress() {
            return progress;
        }

        /**
         * 
         * @return task key for find it in taskmap
         */
        public K getKey() {
            return key;
        }

    }

}
