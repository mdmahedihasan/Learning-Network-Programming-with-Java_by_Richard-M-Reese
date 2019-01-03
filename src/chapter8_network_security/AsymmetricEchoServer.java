package chapter8_network_security;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PrivateKey;

public class AsymmetricEchoServer {

    public static void main(String[] args) {
        System.out.println("Simple Echo Server");
        try (ServerSocket serverSocket = new ServerSocket(6000)) {
            System.out.println("Waiting for connection...");
            Socket socket = serverSocket.accept();
            System.out.println("Connected to client");

            try (DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                 PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true)) {
                byte[] inputBytes = new byte[171];

                PrivateKey privateKey = AsymmetricKeyUtility.getPrivateKey();

                while (true) {
                    int length = dataInputStream.read(inputBytes);
                    String buffer = AsymmetricKeyUtility.decrypt(privateKey, inputBytes);
                    System.out.println("Client request : " + buffer);

                    if ("quit".equalsIgnoreCase(buffer)) {
                        break;
                    }

                    printWriter.println(buffer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Simple Echo Server Terminating");
    }
}
