package chapter7_network_scalability;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;
import java.util.Random;

public class SocketChannelTimeClient {

    public static void main(String[] args) {
        Random random = new Random();
        SocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 5000);

        try (SocketChannel socketChannel = SocketChannel.open(socketAddress)) {
            while (true) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(64);
                int bytesRead = socketChannel.read(byteBuffer);
                while (bytesRead > 0) {
                    byteBuffer.flip();
                    while (byteBuffer.hasRemaining()) {
                        System.out.print((char) byteBuffer.get());
                    }
                    System.out.println();
                    bytesRead = socketChannel.read(byteBuffer);
                }
                Thread.sleep(random.nextInt(1000) + 1000);
            }
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
