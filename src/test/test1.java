package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class test1 {

    public static void main(String[] args) {
        try {
            InetAddress[] inetAddresses = InetAddress.getAllByName("www.google.com");
            for (InetAddress element : inetAddresses) {
                System.out.println(element);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
