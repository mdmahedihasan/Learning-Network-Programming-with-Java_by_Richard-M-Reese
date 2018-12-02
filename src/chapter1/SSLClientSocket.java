package chapter1;

import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SSLClientSocket {

    public static void main(String[] args) throws Exception {
        System.out.println("SSLClientSocket Started");
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try (Socket socket = factory.createSocket("localhost", 8000);
             PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Enter text : ");
                String line = scanner.nextLine();
                if ("quit".equalsIgnoreCase(line)) {
                    break;
                }
                printWriter.println(line);
                System.out.println("Server response : " + bufferedReader.readLine());
            }
            System.out.println("SSLServerSocket Terminated");
        }
    }
}
