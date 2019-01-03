package chapter8_network_security;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.security.PublicKey;
import java.util.Scanner;

public class AsymmetricEchoClient {

    public static void main(String[] args) {
        System.out.println("Simple Echo Client");

        try (Socket socket = new Socket(InetAddress.getLocalHost(), 6000);
             DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             DataInputStream dataInputStream = new DataInputStream(socket.getInputStream())) {
            System.out.println("Connected to server");
            Scanner scanner = new Scanner(System.in);

            PublicKey publicKey = AsymmetricKeyUtility.getPublicKey();

            while (true) {
                System.out.print("Enter text : ");
                String inputLine = scanner.nextLine();

                byte[] encodedData = AsymmetricKeyUtility.encrypt(publicKey, inputLine);
                System.out.println(encodedData);

                dataOutputStream.write(encodedData);
                if ("quit".equalsIgnoreCase(inputLine)) {
                    break;
                }

                String message = bufferedReader.readLine();
                System.out.println("Server response : " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
