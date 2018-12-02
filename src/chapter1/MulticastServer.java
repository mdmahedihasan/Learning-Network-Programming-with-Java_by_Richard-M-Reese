package chapter1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;

public class MulticastServer {

    public static void main(String[] args) {
        System.out.println("Multicast Time Server");
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket();
            while (true) {
                String dateText = new Date().toString();
                byte[] bytes = new byte[256];
                bytes = dateText.getBytes();

                InetAddress inetAddress = InetAddress.getByName("224.0.0.0");
                DatagramPacket datagramPacket;
                datagramPacket = new DatagramPacket(bytes, bytes.length, inetAddress, 8888);
                datagramSocket.send(datagramPacket);
                System.out.println("Time sent : " + dateText);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
