package chapter6_udp_and_multicasting;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.MembershipKey;

public class UDPDatagramMulticastClient {

    public static void main(String[] args) throws Exception {
        System.setProperty("java.net.preferIPv6Stack", "true");
        NetworkInterface networkInterface = NetworkInterface.getByName("eth1");
        DatagramChannel datagramChannel = DatagramChannel.open().bind(new InetSocketAddress(9003))
                .setOption(StandardSocketOptions.IP_MULTICAST_IF, networkInterface);

        InetAddress inetAddress = InetAddress.getByName("FF01:0:0:0:0:0:0:FC");
        MembershipKey membershipKey = datagramChannel.join(inetAddress, networkInterface);

        System.out.println("Joined Multicast Group : " + membershipKey);
        System.out.println("Waiting for a message...");

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        datagramChannel.receive(byteBuffer);

        byteBuffer.flip();
        System.out.print("Received : [ ");
        StringBuilder stringBuilder = new StringBuilder();
        while (byteBuffer.hasRemaining()) {
            stringBuilder.append((char) byteBuffer.get());
        }
        System.out.println(stringBuilder + " ]");
        membershipKey.drop();
    }
}
