package chapter4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    public WebServer() {
        System.out.println("WebServer Started");
        try (ServerSocket serverSocket = new ServerSocket(3000)) {
            while (true) {
                System.out.println("Waiting for client request");
                Socket socket = serverSocket.accept();
                System.out.println("Connection made");
                new Thread(new ClientHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new WebServer();
    }
}
