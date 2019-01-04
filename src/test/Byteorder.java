package test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Byteorder {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        System.out.println(byteBuffer.order());
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        System.out.println(byteBuffer.order());
        ByteBuffer slice = byteBuffer.slice();
        System.out.println(byteBuffer.order());
    }
}
