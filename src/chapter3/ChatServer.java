package chapter3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class ChatServer {

    public ChatServer() {
        System.out.println("Chat Server Started");
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(5000));

            boolean isRunning = true;
            while (isRunning) {
                System.out.println("Waiting for request...");
                SocketChannel socketChannel = serverSocketChannel.accept();
                System.out.println("Connected to Client");

                String message;
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    System.out.print("> ");
                    message = scanner.nextLine();
                    if (message.equalsIgnoreCase("quit")) {
                        HelperMethods.sendMessage(socketChannel, "Server Terminating");
                        isRunning = false;
                        break;
                    } else {
                        HelperMethods.sendMessage(socketChannel, message);
                        System.out.println("Waiting for message from client...");
                        System.out.println("Message : " + HelperMethods.receiveMessage(socketChannel));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ChatServer();
    }
}
