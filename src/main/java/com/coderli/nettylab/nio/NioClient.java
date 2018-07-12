package com.coderli.nettylab.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @author lihongzhe 2018/7/12 15:54
 */
public class NioClient {

    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 7090));
            socketChannel.configureBlocking(false);
            Selector selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
