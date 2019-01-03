package chapter7_network_scalability;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class SimpleClient {

    public static void main(String[] args) {
        System.out.println("Client Started");
        try {
            Socket socket = new Socket("127.0.0.1", 5000);
            System.out.println("Connected to a Server");
            socket.setSoTimeout(3000);
            PrintStream printStream = new PrintStream(socket.getOutputStream());
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String partName = "Axle";
            printStream.println(partName);
            System.out.println(partName + " request sent");
            System.out.println("Response : " + bufferedReader.readLine());
            socket.close();

            socket = new Socket("127.0.0.1", 5000);
            System.out.println("Connected to a Server");
            printStream = new PrintStream(socket.getOutputStream());
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);

            partName = "Wheel";
            printStream.println(partName);
            System.out.println(partName + " request sent");
            System.out.println("Response : " + bufferedReader.readLine());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Client Terminated");
    }
}
