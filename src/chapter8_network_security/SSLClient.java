package chapter8_network_security;

import com.sun.net.ssl.internal.ssl.Provider;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.Security;
import java.util.Scanner;

public class SSLClient {

    public static void main(String[] args) throws Exception {
        System.out.println("SSL Client Started");
        Security.addProvider(new Provider());
        System.setProperty("javax.net.ssl.trustStore", "keystore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "password");

        SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket("localhost", 5000);
        System.out.println("Connection to SSL Server Established");

        PrintWriter printWriter = new PrintWriter(sslSocket.getOutputStream(), true);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter a message : ");
            String message = scanner.nextLine();
            printWriter.println(message);
            System.out.println("Sending : " + bufferedReader.readLine());
            if ("quit".equalsIgnoreCase(message)) {
                break;
            }
        }
        printWriter.close();
        bufferedReader.close();
        sslSocket.close();
    }
}
