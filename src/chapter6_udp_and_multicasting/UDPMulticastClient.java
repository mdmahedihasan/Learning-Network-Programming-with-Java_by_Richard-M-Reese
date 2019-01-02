package chapter6_udp_and_multicasting;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class UDPMulticastClient {

    public UDPMulticastClient() {
        System.out.println("UDP Multicast Time Client Started");
        try {
            MulticastSocket multicastSocket = new MulticastSocket(9877);
            InetAddress inetAddress = InetAddress.getByName("228.5.6.7");
            multicastSocket.joinGroup(inetAddress);

            byte[] bytes = new byte[256];
            DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
            while (true) {
                multicastSocket.receive(datagramPacket);
                String message = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                System.out.println("Message from : " + datagramPacket.getAddress() + " Message : [ " + message + " ]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("UDP Multicast Time Client Terminated");
    }

    public static void main(String[] args) {
        new UDPMulticastClient();
    }
}
