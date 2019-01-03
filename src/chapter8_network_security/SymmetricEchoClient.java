package chapter8_network_security;

import javax.crypto.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

public class SymmetricEchoClient {

    public static String encrypt(String plainText, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            byte[] plainTextBytes = plainText.getBytes();
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(plainTextBytes);
            Base64.Encoder encoder = Base64.getEncoder();
            String encryptedText = encoder.encodeToString(encryptedBytes);
            return encryptedText;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static SecretKey getSecretKey() {
        SecretKey secretKey = null;
        try {
            File file = new File("secretkeystore.jks");
            final KeyStore keyStore = KeyStore.getInstance("JCEKS");
            keyStore.load(new FileInputStream(file), "keystorepassword".toCharArray());
            KeyStore.PasswordProtection keyPassword = new KeyStore.PasswordProtection("keypassword".toCharArray());
            KeyStore.Entry entry = keyStore.getEntry("secretkey", keyPassword);
            secretKey = ((KeyStore.SecretKeyEntry) entry).getSecretKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return secretKey;
    }

    public static void main(String[] args) {
        System.out.println("Simple Echo Client");

        try (Socket socket = new Socket(InetAddress.getLocalHost(), 6000);
             PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            System.out.println("Connected to server");
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Enter text : ");
                String inputLine = scanner.nextLine();
                if ("quit".equalsIgnoreCase(inputLine)) {
                    break;
                }
                String encryptedText = encrypt(inputLine, getSecretKey());
                System.out.println("Encrypted Text After Encryption : " + encryptedText);
                printWriter.println(encryptedText);

                String response = bufferedReader.readLine();
                System.out.println("Server response : " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
