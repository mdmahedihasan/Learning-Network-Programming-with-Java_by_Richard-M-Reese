package chapter5_peer_to_peer_networks;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Chapter5 {

    public static void main(String[] args) {
        shaExamples();
    }

    private static void shaExamples() {
        String message = "String to be hashed";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(message.getBytes());
            byte[] bytes = messageDigest.digest();

            StringBuffer stringBuffer = new StringBuffer();
            for (byte element : bytes) {
                stringBuffer.append(Integer.toString((element & 0xff) + 0x100, 16).substring(1));
            }
            System.out.println("Hex format : " + stringBuffer.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
