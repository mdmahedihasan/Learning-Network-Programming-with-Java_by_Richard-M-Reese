package chapter6_udp_and_multicasting;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Date;

public class UDPMulticastServer {

    public UDPMulticastServer() {
        System.out.println("UDP Multicast Time Server Started");
        try {
            MulticastSocket multicastSocket = new MulticastSocket();
            InetAddress inetAddress = InetAddress.getByName("228.5.6.7");
            multicastSocket.joinGroup(inetAddress);

            byte[] bytes;
            DatagramPacket datagramPacket;
            while (true) {
                Thread.sleep(1000);
                String message = (new Date()).toString();
                System.out.println("Sending : [ " + message + " ]");
                bytes = message.getBytes();
                datagramPacket = new DatagramPacket(bytes, message.length(), inetAddress, 9877);
                multicastSocket.send(datagramPacket);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("UDP Multicast Time Server Terminated");
    }

    public static void main(String[] args) {
        new UDPMulticastServer();
    }
}
