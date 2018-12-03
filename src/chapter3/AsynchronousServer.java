package chapter3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
//import java.util.concurrent.TimeUnit;

public class AsynchronousServer {

    public AsynchronousServer() {
        System.out.println("Asynchronous Server Started");
        try (AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open()) {
            InetSocketAddress hostAddress = new InetSocketAddress("localhost", 5000);
            serverChannel.bind(hostAddress);

            System.out.println("Waiting for client to connect...");
            Future acceptResult = serverChannel.accept();

            try (AsynchronousSocketChannel clientChannel = (AsynchronousSocketChannel) acceptResult.get()) {
                System.out.println("Message from client : ");
                while ((clientChannel != null) && (clientChannel.isOpen())) {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(32);
                    Future future = clientChannel.read(byteBuffer);

                    while (!future.isDone()) {
                        //
                    }
                    //future.get();
                    //future.get(10, TimeUnit.SECONDS);
                    byteBuffer.flip();

                    String message = new String(byteBuffer.array()).trim();
                    System.out.println(message);
                    if (message.equals("quit")) {
                        break;
                    }
                }
            } catch (ExecutionException | InterruptedException | IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AsynchronousServer();
    }
}
