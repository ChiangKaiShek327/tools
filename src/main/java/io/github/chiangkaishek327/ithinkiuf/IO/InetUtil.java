package io.github.chiangkaishek327.ithinkiuf.IO;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

public class InetUtil {
    /**
     * 
     * @param soc the socket need get ip
     * @return what host the socket connect
     */
    public static String getIP(Socket soc) {
        return soc.getInetAddress().getHostName();
    }

    /**
     * 
     * @return the port can be used
     * @throws IOException
     */
    public static int getFreePort() throws IOException {
        ServerSocket socket = new ServerSocket(0);
        int port = socket.getLocalPort();
        socket.close();
        return port;
    }

    /**
     * 
     * @param port the port need check
     * @return is the port used
     */
    public static boolean isPortUsed(int port) {
        try {
            Socket socket = new Socket("localhost", port);
            socket.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 
     * @param url Target http Url
     * @return Length of content
     * @throws IOException
     */
    public static long getLength(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        String len = connection.getHeaderField("Content-Length");
        return len != null ? Long.parseLong(len) : -1;
    }
}
