package chapter5_peer_to_peer_networks;

import org.kth.dks.JDHT;

import java.io.IOException;
import java.util.Scanner;

public class JDHTExample {

    public static void main(String[] args) {
        try {
            JDHT jdht = new JDHT();
            jdht.put("Java SE API", "http://docs.oracle.com/javase/8/docs/api/");
            System.out.println(((JDHT) jdht).getReference());
            Scanner scanner = new Scanner(System.in);
            System.out.println("Press Enter to terminate application : ");
            scanner.next();
            jdht.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
