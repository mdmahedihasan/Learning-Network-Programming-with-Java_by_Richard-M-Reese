package chapter1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class UDPClient {

    public static void main(String[] args) throws Exception {
        final int port = 9876;

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
             DatagramSocket datagramSocket = new DatagramSocket()) {
            String host = "127.0.0.1";
            InetAddress inetAddress = InetAddress.getByName(host);

            System.out.print("Enter text : ");

            byte[] data = bufferedReader.readLine().getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(data, data.length, inetAddress, port);
            datagramSocket.send(datagramPacket);

            byte[] receiveData = new byte[1024];
            DatagramPacket datagramPacket1 = new DatagramPacket(receiveData, receiveData.length);
            System.out.println("Waiting for return packet");
            try {
                datagramSocket.receive(datagramPacket1);
                System.out.println("Message : " + new String(datagramPacket1.getData()).trim());
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
            }
        }
    }
}
