package chapter1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastClient {

    public static void main(String[] args) {
        System.out.println("Multicast Time Client");
        try (MulticastSocket multicastSocket = new MulticastSocket(8888)) {
            InetAddress inetAddress = InetAddress.getByName("224.0.0.0");
            multicastSocket.joinGroup(inetAddress);
            System.out.println("Multicast Group Joined");

            byte[] bytes = new byte[256];
            DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
            for (int i = 0; i < 5; i++) {
                multicastSocket.receive(datagramPacket);

                String receivedText = new String(datagramPacket.getData());
                System.out.println(receivedText.trim());
            }
            multicastSocket.leaveGroup(inetAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Multicast Time Client Terminated");
    }
}
