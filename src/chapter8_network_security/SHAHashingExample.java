package chapter8_network_security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAHashingExample {

    public static void main(String[] args) throws Exception {
        SHAHashingExample example = new SHAHashingExample();
        String message = "The message";
        byte[] bytes = example.getHashValue(message);
        example.displayHashValue(bytes);
    }

    public void displayHashValue(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            stringBuilder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        System.out.println("Hash Value : " + stringBuilder.toString());
    }

    public byte[] getHashValue(String message) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(message.getBytes());
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
