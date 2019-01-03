package chapter7_network_scalability;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPool {

    public static void main(String[] args) {
        System.out.println("Thread Pool Server Started");
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            while (true) {
                System.out.println("Listening for a client connection");
                Socket socket = serverSocket.accept();
                System.out.println("Connected to a Client");
                WorkerThread workerThread = new WorkerThread(socket);
                System.out.println("Task created : " + workerThread);
                executor.execute(workerThread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        System.out.println("Thread Pool Server Terminated");
    }
}
