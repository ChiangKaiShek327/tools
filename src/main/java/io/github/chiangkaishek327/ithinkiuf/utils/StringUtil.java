package io.github.chiangkaishek327.ithinkiuf.utils;

public class StringUtil {
    /**
     * 
     * @param source source text
     * @param times  repeat times
     * @return
     */
    public static String repeat(String source, long times) {
        StringBuilder sb = new StringBuilder();
        for (long i = 0; i < times; i++) {
            sb.append(source);
        }
        return sb.toString();
    }
}
