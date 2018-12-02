package chapter1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ThreadedEchoServer implements Runnable {

    private static Socket socket;

    public ThreadedEchoServer(Socket socket) {
        this.socket = socket;
    }

    public static void main(String[] args) {
        System.out.println("Threaded Echo Server");
        try (ServerSocket serverSocket = new ServerSocket(6000)) {
            while (true) {
                System.out.println("Waiting for connection...");

                socket = serverSocket.accept();
                ThreadedEchoServer threadedEchoServer = new ThreadedEchoServer(socket);
                new Thread(threadedEchoServer).start();
            }
        } catch (IOException e) {
            //System.out.println(e);
            e.printStackTrace();
        }
        System.out.println("Threaded Echo Server Terminating");
    }

    @Override
    public void run() {
        System.out.println("Connected to client using [" + Thread.currentThread() + "]");
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true)) {
            /**String line;
             while ((line = bufferedReader.readLine()) != null) {
             System.out.println("Client request [" + Thread.currentThread() + "] : " + line);
             printWriter.println(line);
             }
             System.out.println("Client [" + Thread.currentThread() + " connection terminated");**/
            Supplier<String> supplier = () -> {
                try {
                    return bufferedReader.readLine();
                } catch (IOException e) {
                    return null;
                }
            };
            Stream<String> stream = Stream.generate(supplier);
            stream
                    .map(s -> {
                        System.out.println("Client request : " + s);
                        printWriter.println(s);
                        return s;
                    })
                    .allMatch(s -> s != null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
