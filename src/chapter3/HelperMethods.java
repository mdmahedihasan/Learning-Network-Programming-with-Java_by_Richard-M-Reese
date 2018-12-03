package chapter3;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class HelperMethods {

    public static void sendFixedLengthMessage(SocketChannel socketChannel, String message) {
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(64);
            byteBuffer.put(message.getBytes());
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                socketChannel.write(byteBuffer);
            }
            System.out.println("Sent : " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String receiveFixedLengthMessage(SocketChannel socketChannel) {
        String message = "";
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(64);
            socketChannel.read(byteBuffer);
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                message += (char) byteBuffer.get();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    public static void sendMessage(SocketChannel socketChannel, String message) {
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(message.length() + 1);
            byteBuffer.put(message.getBytes());
            byteBuffer.put((byte) 0x00);
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                socketChannel.write(byteBuffer);
            }
            System.out.println("Sent : " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String receiveMessage(SocketChannel socketChannel) {
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(16);
            String message = "";
            while (socketChannel.read(byteBuffer) > 0) {
                char byteRead = 0x00;
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()) {
                    byteRead = (char) byteBuffer.get();
                    if (byteRead == 0x00) {
                        break;
                    }
                    message += byteRead;
                }
                if (byteRead == 0x00) {
                    break;
                }
                byteBuffer.clear();
            }
            return message;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
