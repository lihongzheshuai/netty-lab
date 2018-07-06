package com.coderli.nettylab.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lihongzhe 2018-06-19 11:06
 */
public class BioServer {

    public static void main(String[] args) throws IOException {
        ServerSocket socketServer = new ServerSocket();
        socketServer.bind(new InetSocketAddress("127.0.0.1", 7080));
        for (; ; ) {
            Socket socket = socketServer.accept();
            System.out.println("接收到新的连接请求。");
            InputStream is = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();
            System.out.println(line);
            socket.getOutputStream().write("Hello, I'm server.".getBytes());
            socket.shutdownOutput();
        }
    }

}
