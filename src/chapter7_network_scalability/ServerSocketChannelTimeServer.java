package chapter7_network_scalability;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class ServerSocketChannelTimeServer {

    private static Selector selector;

    static class SelectorHandler implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("About to select...");
                    int readyChannels = selector.select(500);
                    if (readyChannels == 0) {
                        System.out.println("No tasks available");
                    } else {
                        Set<SelectionKey> keys = selector.selectedKeys();
                        Iterator<SelectionKey> keyIterator = keys.iterator();

                        while (keyIterator.hasNext()) {
                            SelectionKey selectionKey = keyIterator.next();

                            if (selectionKey.isAcceptable()) {

                            } else if (selectionKey.isConnectable()) {

                            } else if (selectionKey.isReadable()) {

                            } else if (selectionKey.isWritable()) {
                                String message = "Date : " + new Date(System.currentTimeMillis());
                                ByteBuffer byteBuffer = ByteBuffer.allocate(64);
                                byteBuffer.put(message.getBytes());
                                byteBuffer.flip();
                                SocketChannel socketChannel = null;
                                while (byteBuffer.hasRemaining()) {
                                    socketChannel = (SocketChannel) selectionKey.channel();
                                    socketChannel.write(byteBuffer);
                                }
                                System.out.println("Sent : " + message + " to : " + socketChannel);
                            }
                            Thread.sleep(1000);
                            keyIterator.remove();
                        }
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Time Server Started");
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(5000));
            selector = Selector.open();
            new Thread(new SelectorHandler()).start();
            while (true) {
                SocketChannel socketChannel = serverSocketChannel.accept();
                System.out.println("Socket channel accepted - " + socketChannel);

                if (socketChannel != null) {
                    socketChannel.configureBlocking(false);
                    selector.wakeup();
                    socketChannel.register(selector, SelectionKey.OP_WRITE, null);
                }
            }
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
