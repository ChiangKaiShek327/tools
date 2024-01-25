package io.github.chiangkaishek327.tools.IO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtil {
    /**
     * 
     * @param dir target dir
     * @return size of all files under directory
     * @throws IOException
     */
    public static long getDirectorySize(File dir) throws IOException {
        long size = 0;
        if (!dir.isDirectory())
            throw new NotDirectoryException(dir + " isn't a directory");
        File[] files = dir.listFiles();
        if (files != null)
            for (File file : files) {
                if (file.isFile()) {
                    size = size + file.length();
                } else if (file.isDirectory()) {
                    size = size + getDirectorySize(file);
                }
            }
        return size;
    }

    /**
     * 
     * @param location target file location
     * @return file contents as string
     * @throws IOException
     */
    public static String readAllFileContent(String location) throws IOException {
        return readAllFileContent(location, StandardCharsets.UTF_8);
    }

    /**
     * 
     * @param location target file location
     * @param charset  file charset
     * @return file contents as string
     * @throws IOException
     */

    public static String readAllFileContent(String location, Charset charset) throws IOException {
        return readAllFileContent(new File(location), charset);
    }

    /**
     * 
     * @param file target file
     * @return file contents as string
     * @throws IOException
     */
    public static String readAllFileContent(File file) throws IOException {
        return readAllFileContent(file, StandardCharsets.UTF_8);
    }

    /**
     * 
     * @param file    target file
     * @param charset file charset
     * @return file contents as string
     * @throws IOException
     */
    public static String readAllFileContent(File file, Charset charset) throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(file), charset);
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNextLine()) {
            builder.append(scanner.nextLine() + "\n");

        }
        builder.deleteCharAt(builder.length() - 1);
        scanner.close();
        return builder.toString();
    }

    /**
     * Of course,this method is for compressing zip file
     * 
     * @param files directory of files
     * @param dir   directory of files
     * @return list of file relative path
     * @throws IOException
     */
    public static List<String> getFileRelativePathList(File files, String dir) throws IOException {
        if (!files.exists()) {
            throw new FileNotFoundException("File not found %s".formatted(files.getName()));
        }
        String praseddir = dir.contains("/") ? dir.replaceAll("/", "\\") : dir;
        List<String> nameList = new ArrayList<>();
        for (File file : files.listFiles()) {
            nameList.add(file.getAbsolutePath().replace(praseddir + "\\", ""));
            if (file.isDirectory()) {

                nameList.addAll(getFileRelativePathList(file, praseddir));
            }

        }
        return nameList;
    }

    /**
     * 
     * @param files directory of files
     * @return list of file relative path
     * @throws IOException
     */
    public static List<String> getFileRelativePathList(File files) throws IOException {
        return getFileRelativePathList(files, files.getAbsolutePath());
    }
}
