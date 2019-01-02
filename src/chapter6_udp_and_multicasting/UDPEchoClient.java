package chapter6_udp_and_multicasting;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UDPEchoClient {

    public static void main(String[] args) {
        System.out.println("UDP Echo Client Started");
        try {
            SocketAddress remoteSocketAddress = new InetSocketAddress("127.0.0.1", 9000);
            DatagramChannel datagramChannel = DatagramChannel.open();
            datagramChannel.connect(remoteSocketAddress);

            String message = "The message";
            ByteBuffer byteBuffer = ByteBuffer.allocate(message.length());
            byteBuffer.put(message.getBytes());

            byteBuffer.flip();
            datagramChannel.write(byteBuffer);
            System.out.println("Sent : [ " + message + " ]");

            byteBuffer.clear();
            datagramChannel.read(byteBuffer);
            byteBuffer.flip();
            System.out.print("Received : [");
            while (byteBuffer.hasRemaining()) {
                System.out.print((char) byteBuffer.get());
            }
            System.out.println(" ]");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("UDP Echo Client Terminated");
    }
}
