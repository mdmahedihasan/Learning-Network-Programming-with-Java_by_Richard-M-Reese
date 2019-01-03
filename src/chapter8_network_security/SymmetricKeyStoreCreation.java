package chapter8_network_security;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class SymmetricKeyStoreCreation {

    private static KeyStore createKeyStore(String fileName, String password) {
        try {
            File file = new File(fileName);
            final KeyStore keyStore = KeyStore.getInstance("JCEKS");

            if (file.exists()) {
                keyStore.load(new FileInputStream(file), password.toCharArray());
            } else {
                keyStore.load(null, null);
                keyStore.store(new FileOutputStream(fileName), password.toCharArray());
            }
            return keyStore;
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            final String keyStoreFile = "secretkeystore.jks";
            KeyStore keyStore = createKeyStore(keyStoreFile, "keystorepassword");
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecretKey secretKey = keyGenerator.generateKey();

            KeyStore.SecretKeyEntry keyStoreEntry = new KeyStore.SecretKeyEntry(secretKey);
            KeyStore.PasswordProtection keyPassword = new KeyStore.PasswordProtection("keypassword".toCharArray());
            keyStore.setEntry("secretKey", keyStoreEntry, keyPassword);
            keyStore.store(new FileOutputStream(keyStoreFile), "keystorepassword".toCharArray());
            System.out.println(secretKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
