package chapter7_network_scalability;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.NumberFormat;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleMultiThreadedServer implements Runnable {

    private static final ConcurrentHashMap<String, Float> map;
    private final Socket clientSocket;

    static {
        map = new ConcurrentHashMap<>();
        map.put("Axle", 238.50f);
        map.put("Gear", 45.55f);
        map.put("Wheel", 86.30f);
        map.put("Rotor", 8.50f);
    }

    SimpleMultiThreadedServer(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public static void main(String[] args) {
        System.out.println("Multi-Threaded Server Started");
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            while (true) {
                System.out.println("Listening for a client connection");
                Socket socket = serverSocket.accept();
                System.out.println("Connected to a Client");
                new Thread(new SimpleMultiThreadedServer(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Multi-Threaded Server Terminated");
    }

    @Override
    public void run() {
        System.out.println("Client Thread Started");
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintStream printStream = new PrintStream(clientSocket.getOutputStream())) {
            String partName = bufferedReader.readLine();
            float price = map.get(partName);
            printStream.println(price);
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
            System.out.println("Request for " + partName + " and returned a price of" + numberFormat.format(price));
            clientSocket.close();
            System.out.println("Client Connection Terminated");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Client Thread Terminated");
    }
}
