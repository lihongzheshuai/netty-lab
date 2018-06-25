package com.coderli.nettylab.bio;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketImpl;

/**
 * @author OneCoder 2018-06-21 23:24
 */
public class BioClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1", 7080));
        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();
        os.write("Hello, I'm client.".getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        System.out.println(br.readLine());
        socket.close();
    }

}
