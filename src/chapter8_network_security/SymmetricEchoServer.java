package chapter8_network_security;

import javax.crypto.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Base64;

public class SymmetricEchoServer {

    public static String decrypt(String encryptedText, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] encryptedBytes = decoder.decode(encryptedText);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            String decryptedText = new String(decryptedBytes);
            return decryptedText;
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
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            e.printStackTrace();
        }
        return secretKey;
    }

    public static void main(String[] args) {
        System.out.println("Simple Echo Server");
        try (ServerSocket serverSocket = new ServerSocket(6000)) {
            System.out.println("Waiting for connection...");

            Socket socket = serverSocket.accept();
            System.out.println("Connected to client");

            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true)) {
                String inputLine;
                while ((inputLine = bufferedReader.readLine()) != null) {
                    String decryptedText = decrypt(inputLine, getSecretKey());
                    System.out.println("Client request : " + decryptedText);
                    printWriter.println(inputLine);
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
