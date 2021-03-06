package com.coderli.nettylab.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author lihongzhe 2018/7/11 10:59
 */
public class NioServer {

    public static void main(String[] args) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 7090));
            serverSocketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            for (; ; ) {
                selector.select();
                Iterator<SelectionKey> keysItor = selector.selectedKeys().iterator();
                while (keysItor.hasNext()) {
                    SelectionKey selectionKey = keysItor.next();
                    keysItor.remove();
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel ssChannel = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel socketChannel = ssChannel.accept();
                        socketChannel.configureBlocking(false);
                        ByteBuffer buffer = ByteBuffer.allocate(17);
                        socketChannel.read(buffer);
                        System.out.println("Receive msg from client:" + new String(buffer.array()));
                        socketChannel.write(ByteBuffer.wrap(new String("Server: op_accept").getBytes()));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
