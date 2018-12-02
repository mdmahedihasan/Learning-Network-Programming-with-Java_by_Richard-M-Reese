package chapter1;

import javax.net.ssl.SSLServerSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SSLServerSocket {

    public static void main(String[] args) {
        try {
            SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            ServerSocket serverSocket = factory.createServerSocket(8000);
            System.out.println("SSLServerSocket Started");
            try (Socket socket = serverSocket.accept();
                 PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                System.out.println("Client socket created");
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                    printWriter.println(line);
                }
                bufferedReader.close();
                System.out.println("SSLServerSocket Terminated");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
