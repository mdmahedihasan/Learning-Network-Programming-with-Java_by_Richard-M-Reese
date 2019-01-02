package chapter6_udp_and_multicasting;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Collections;
import java.util.Enumeration;

public class UDPDatagramMulticastServer {

    public static void main(String[] args) {
        try {
            Enumeration<NetworkInterface> networkInterfaceEnumeration;
            networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface networkInterface : Collections.list(networkInterfaceEnumeration)) {
                displayNetworkInterfaceInformation(networkInterface);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        try {
            System.setProperty("java.net.preferIPv6Stack", "true");
            DatagramChannel datagramChannel = DatagramChannel.open();
            NetworkInterface networkInterface = NetworkInterface.getByName("eth1");
            datagramChannel.setOption(StandardSocketOptions.IP_MULTICAST_IF, networkInterface);
            InetSocketAddress inetSocketAddress = new InetSocketAddress("FF01:0:0:0:0:0:0:FC", 9003);

            String message = "The message";
            ByteBuffer byteBuffer = ByteBuffer.allocate(message.length());
            byteBuffer.put(message.getBytes());

            while (true) {
                datagramChannel.send(byteBuffer, inetSocketAddress);
                System.out.println("Sent the multicast message : " + message);
                byteBuffer.clear();

                byteBuffer.mark();
                System.out.println("Sent : [ ");
                StringBuilder stringBuilder = new StringBuilder();
                while (byteBuffer.hasRemaining()) {
                    stringBuilder.append((char) byteBuffer.get());
                }
                System.out.println(stringBuilder + " ]");
                byteBuffer.reset();
                Thread.sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void displayNetworkInterfaceInformation(NetworkInterface networkInterface) {
        try {
            System.out.printf("Display name : %s\n", networkInterface.getDisplayName());
            System.out.printf("Name : %s\n", networkInterface.getName());
            System.out.printf("Supports Multicast : %s\n", networkInterface.supportsMulticast());
            Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses();
            for (InetAddress inetAddress : Collections.list(inetAddressEnumeration)) {
                System.out.printf("InetAddress : %s\n", inetAddress);
            }
            System.out.println();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
