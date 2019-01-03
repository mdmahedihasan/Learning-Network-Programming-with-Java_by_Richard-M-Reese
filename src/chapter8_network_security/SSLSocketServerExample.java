package chapter8_network_security;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;

public class SSLSocketServerExample {

    private SSLServerSocket sslServerSocket;

    public static void main(String[] args) {
        new SSLSocketServerExample(4444);
    }

    public SSLSocketServerExample(int port) {
        try {
            System.setProperty("javax.net.ssl.keyStore", "keystore.jks");
            System.setProperty("javax.net.ssl.keyStorePassword", "password");

            SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(port);
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        while (true) {
            try {
                SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();
                System.out.println("User connected successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
