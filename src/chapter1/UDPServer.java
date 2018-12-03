package chapter1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer {

    public static void main(String[] args) throws Exception {
        final int port = 9876;

        try {
            DatagramSocket datagramSocket = new DatagramSocket(port);
            while (true) {
                byte[] bytes = new byte[1024];
                DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
                System.out.println("Waiting for datagram packet");
                datagramSocket.receive(datagramPacket);

                String message = new String(datagramPacket.getData());

                InetAddress inetAddress = datagramPacket.getAddress();
                int clientPort = datagramPacket.getPort();

                System.out.println("Message : " + message.trim());

                byte[] bytes1 = message.getBytes();
                DatagramPacket datagramPacket1 = new DatagramPacket(bytes1, bytes1.length, inetAddress, clientPort);
                datagramSocket.send(datagramPacket1);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
