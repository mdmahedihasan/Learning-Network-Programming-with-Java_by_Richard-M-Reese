package chapter3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsynchronousClient {

    public static void main(String[] args) {
        System.out.println("Asynchronous Client Started");
        try (AsynchronousSocketChannel clientChannel = AsynchronousSocketChannel.open()) {
            InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 5000);
            Future future = clientChannel.connect(inetSocketAddress);
            future.get();

            System.out.println("Client is started : " + clientChannel.isOpen());
            System.out.println("Sending messages to server : ");

            Scanner scanner = new Scanner(System.in);
            String message;
            while (true) {
                System.out.print("> ");
                message = scanner.nextLine();
                ByteBuffer byteBuffer = ByteBuffer.wrap(message.getBytes());
                Future result = clientChannel.write(byteBuffer);
                while (!result.isDone()) {

                }
                if (message.equalsIgnoreCase("quit")) {
                    break;
                }
            }
        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
