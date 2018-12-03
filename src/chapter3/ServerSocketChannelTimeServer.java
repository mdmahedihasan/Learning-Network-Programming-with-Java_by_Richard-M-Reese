package chapter3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;

public class ServerSocketChannelTimeServer {

    public static void main(String[] args) {
        System.out.println("Time Server Started");
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(5000));
            while (true) {
                System.out.println("Waiting for request...");
                SocketChannel socketChannel = serverSocketChannel.accept();

                if (socketChannel != null) {
                    String message = "Date : " + new Date(System.currentTimeMillis());
                    ByteBuffer byteBuffer = ByteBuffer.allocate(64);
                    byteBuffer.put(message.getBytes());
                    byteBuffer.flip();
                    while (byteBuffer.hasRemaining()) {
                        socketChannel.write(byteBuffer);
                    }
                    System.out.println("Sent : " + message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
