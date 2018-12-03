package chapter3;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Chapter3 {

    public static void main(String[] args) {
        creatingBuffers();
        bulkTransferExample();
    }

    private static void creatingBuffers() {
        String contents = "Book";
        ByteBuffer byteBuffer = ByteBuffer.allocate(32);
        byteBuffer.put(contents.getBytes());

        ByteBuffer duplicateBuffer = byteBuffer.duplicate();
        duplicateBuffer.put(0, (byte) 0x4c);

        System.out.println("byteBuffer : " + byteBuffer.get(0));
        System.out.println("duplicateBuffer : " + duplicateBuffer.get(0));
        System.out.println();

        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        System.out.println("Read only : " + readOnlyBuffer.isReadOnly());
    }

    private static void displayBuffer(IntBuffer intBuffer) {
        int arr[] = new int[intBuffer.position()];
        intBuffer.rewind();
        intBuffer.get(arr);

        for (int element : arr) {
            System.out.print(element + " ");
        }
        System.out.println();

        for (int i = 0; i < intBuffer.position(); i++) {
            System.out.print(intBuffer.get(i) + " ");
        }
        System.out.println();
    }

    private static void bulkTransferExample() {
        int[] arr = {12, 51, 79, 54};
        IntBuffer intBuffer = IntBuffer.allocate(6);
        System.out.println(intBuffer);

        intBuffer.put(arr);
        System.out.println(intBuffer);
        displayBuffer(intBuffer);

        int length = intBuffer.remaining();
        intBuffer.put(arr, 0, length);
        System.out.println(intBuffer);
        displayBuffer(intBuffer);
    }
}
