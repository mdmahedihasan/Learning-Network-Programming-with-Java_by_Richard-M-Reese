package chapter9_network_interoperability;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class JavaSocket {

    public static void main(String[] args) {
        System.out.println("Server Started");
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            Socket socket = serverSocket.accept();
            System.out.println("Client connection completed");

            Scanner scanner = new Scanner(socket.getInputStream());
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            String message = scanner.nextLine();
            System.out.println("Server received : " + message);
            printWriter.println(message);
            System.out.println("Server sent : " + message);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server Terminated");
    }
}
