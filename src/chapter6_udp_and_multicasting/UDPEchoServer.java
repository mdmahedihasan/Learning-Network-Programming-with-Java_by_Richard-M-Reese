package chapter6_udp_and_multicasting;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UDPEchoServer {

    public static void main(String[] args) {
        int port = 9000;
        System.out.println("UDP Echo Server Started");
        try (DatagramChannel datagramChannel = DatagramChannel.open();
             DatagramSocket datagramSocket = datagramChannel.socket()) {
            SocketAddress socketAddress = new InetSocketAddress(port);
            datagramSocket.bind(socketAddress);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(65507);
            while (true) {
                SocketAddress clientSocketAddress = datagramChannel.receive(byteBuffer);
                byteBuffer.flip();
                byteBuffer.mark();
                System.out.print("Received : [ ");
                StringBuilder stringBuilder = new StringBuilder();
                while (byteBuffer.hasRemaining()) {
                    stringBuilder.append((char) byteBuffer.get());
                }
                System.out.println(stringBuilder + " ]");
                byteBuffer.reset();

                datagramChannel.send(byteBuffer, clientSocketAddress);
                System.out.println("Sent : [ " + stringBuilder + " ]");
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("UDP Echo Server Terminated");
    }
}
