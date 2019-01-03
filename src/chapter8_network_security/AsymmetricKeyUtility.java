package chapter8_network_security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class AsymmetricKeyUtility {

    private static KeyStore createKeyStore(String fileName, String password) throws Exception {
        File file = new File(fileName);

        final KeyStore keyStore = KeyStore.getInstance("JCEKS");
        if (file.exists()) {
            keyStore.load(new FileInputStream(file), password.toCharArray());
        } else {
            keyStore.load(null, null);
            keyStore.store(new FileOutputStream(fileName), password.toCharArray());
        }
        return keyStore;
    }

    public static void savePrivateKey(PrivateKey privateKey) {
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
            FileOutputStream fileOutputStream = new FileOutputStream("private.key");
            fileOutputStream.write(pkcs8EncodedKeySpec.getEncoded());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PrivateKey getPrivateKey() {
        try {
            File privateKeyFile = new File("private.key");
            FileInputStream fileInputStream = new FileInputStream("private.key");
            byte[] encodedPrivateKeyBytes = new byte[(int) privateKeyFile.length()];
            fileInputStream.read(encodedPrivateKeyBytes);
            fileInputStream.close();

            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            return privateKey;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void savePublicKey(PublicKey publicKey) {
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
            FileOutputStream fileOutputStream = new FileOutputStream("public.key");
            fileOutputStream.write(x509EncodedKeySpec.getEncoded());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PublicKey getPublicKey() {
        try {
            File publicKeyFile = new File("public.key");
            FileInputStream fileInputStream = new FileInputStream("public.key");
            byte[] encodedPublicKeyBytes = new byte[(int) publicKeyFile.length()];
            fileInputStream.read(encodedPublicKeyBytes);
            fileInputStream.close();
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(encodedPublicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            return publicKey;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] encrypt(PublicKey publicKey, String message) {
        byte[] encodedData = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(message.getBytes());
            encodedData = Base64.getEncoder().withoutPadding().encode(encryptedBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                BadPaddingException e) {
            e.printStackTrace();
        }
        return encodedData;
    }

    public static String decrypt(PrivateKey privateKey, byte[] encodedData) {
        String message = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decodedData = Base64.getDecoder().decode(encodedData);
            byte[] decryptedBytes = cipher.doFinal(decodedData);
            message = new String(decryptedBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                BadPaddingException e) {
            e.printStackTrace();
        }
        return message;
    }

    public static void main(String[] args) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            savePrivateKey(privateKey);
            privateKey = getPrivateKey();
            savePublicKey(publicKey);
            publicKey = getPublicKey();

            String message = "The message";
            System.out.println("Message : " + message);

            byte[] encodedData = encrypt(publicKey, message);
            System.out.println("Decrypted Message : " + decrypt(privateKey, encodedData));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
