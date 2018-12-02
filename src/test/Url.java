package test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Url {

    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getByName("www.packtpub.com");
            System.out.println(address);
            System.out.println("Canonical Host Name : " + address.getCanonicalHostName());
            System.out.println("Host Address : " + address.getHostAddress());
            System.out.println("Host Name : " + address.getHostName());
            System.out.println(address.isReachable(10000));
        } catch (UnknownHostException e) {
            System.out.println("Error : " + e);
        } catch (IOException e) {
            System.out.println("Error : " + e);
        }
    }
}
