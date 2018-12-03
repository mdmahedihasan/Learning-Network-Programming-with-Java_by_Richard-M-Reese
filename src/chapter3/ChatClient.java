package chapter3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class ChatClient {

    public ChatClient() {
        SocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 5000);
        try (SocketChannel socketChannel = SocketChannel.open(socketAddress)) {
            System.out.println("Connected to Chat Server");
            String message;
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Waiting for message from the server...");
                System.out.println("Message : " + HelperMethods.receiveMessage(socketChannel));
                System.out.println("> ");
                message = scanner.nextLine();
                if (message.equalsIgnoreCase("quit")) {
                    HelperMethods.sendMessage(socketChannel, "Client terminating");
                    break;
                }
                HelperMethods.sendMessage(socketChannel, message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ChatClient();
    }
}
