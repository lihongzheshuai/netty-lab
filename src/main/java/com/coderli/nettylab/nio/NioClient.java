package com.coderli.nettylab.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author lihongzhe 2018/7/12 15:54
 */
public class NioClient {

    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 7090));
            Selector selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            selector.select();
            Iterator<SelectionKey> itor = selector.selectedKeys().iterator();
            while (itor.hasNext()) {
                SelectionKey key = itor.next();
                if (key.isConnectable()) {
                    System.out.println("Connectable...");
                    while (socketChannel.isConnectionPending()) {
                        socketChannel.finishConnect();
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    }
                    SocketChannel channel = (SocketChannel) key.channel();
                    channel.write(ByteBuffer.wrap("I am client".getBytes()));
                }
            }
            for (; ; ) {
                selector.select();
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (key.isConnectable()) {
                        System.out.println("Connectable...");
                    }
                    if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(17);
                        channel.read(buffer);
                        System.out.println("Receive msg from server:" + new String(buffer.array()));
                        keyIterator.remove();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
