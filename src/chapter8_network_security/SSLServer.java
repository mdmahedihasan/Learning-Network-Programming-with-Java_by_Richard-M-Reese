package chapter8_network_security;

import com.sun.net.ssl.internal.ssl.Provider;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.Security;

public class SSLServer {

    public static void main(String[] args) throws Exception {
        System.out.println("SSL Server Started");
        Security.addProvider(new Provider());
        System.setProperty("javax.net.ssl.keyStore", "keystore.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "password");

        SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(5000);

        System.out.println("Waiting for a connection");
        SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();
        System.out.println("Connection established");

        PrintWriter printWriter = new PrintWriter(sslSocket.getOutputStream(), true);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));

        String inputLine;
        while ((inputLine = bufferedReader.readLine()) != null) {
            printWriter.println(inputLine);
            if ("quit".equalsIgnoreCase(inputLine)) {
                break;
            }
            System.out.println("Receiving : " + inputLine);
        }
        printWriter.close();
        bufferedReader.close();
        sslSocket.close();
        sslServerSocket.close();
    }
}
