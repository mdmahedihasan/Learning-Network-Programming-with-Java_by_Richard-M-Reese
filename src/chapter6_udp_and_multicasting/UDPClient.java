package chapter6_udp_and_multicasting;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {

    public UDPClient() {
        System.out.println("UDP Client Started");
        Scanner scanner = new Scanner(System.in);
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            InetAddress inetAddress = InetAddress.getByName("localhost");
            byte[] sendMessages;
            while (true) {
                System.out.print("Enter a message : ");
                String message = scanner.nextLine();
                if ("quit".equalsIgnoreCase(message)) {
                    break;
                }
                sendMessages = message.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendMessages, sendMessages.length, inetAddress, 9003);
                clientSocket.send(sendPacket);

                byte[] receiveMessages = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveMessages, receiveMessages.length);
                clientSocket.receive(receivePacket);
                String receivedSentence = new String(receivePacket.getData());
                System.out.println("Received from server [ " + receivedSentence + "\nfrom " +
                        receivePacket.getSocketAddress());
                System.out.println("Received from server [ " + receivedSentence.trim() + "]\nfrom " +
                        receivePacket.getSocketAddress());
            }
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("UDP Client Terminating");
    }

    public static void main(String[] args) {
        new UDPClient();
    }
}
