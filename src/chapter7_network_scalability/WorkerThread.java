package chapter7_network_scalability;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.text.NumberFormat;
import java.util.concurrent.*;

public class WorkerThread implements Runnable {

    private static final ConcurrentHashMap<String, Float> map;
    private final Socket clientSocket;

    static {
        map = new ConcurrentHashMap<>();
        map.put("Axle", 11.11f);
        map.put("Gear", 22.22f);
        map.put("Wheel", 33.33f);
        map.put("Rotor", 44.44f);
    }

    public WorkerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        System.out.println("Worker Thread Started");
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintStream printStream = new PrintStream(clientSocket.getOutputStream())) {
            String partName = bufferedReader.readLine();
            //float price = map.get(partName);
            float price = 0.0f;
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
            Future<Float> future1 = executor.submit(new Callable<Float>() {
                @Override
                public Float call() throws Exception {
                    return 1.0f;
                }
            });
            Future<Float> future2 = executor.submit(new Callable<Float>() {
                @Override
                public Float call() throws Exception {
                    return 2.0f;
                }
            });
            try {
                Float firstPart = future1.get();
                Float secondPart = future2.get();
                price = firstPart + secondPart;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            /**try {
             price = new WorkerCallable(partName).call();
             } catch (Exception e) {
             e.printStackTrace();
             }**/
            printStream.println(price);
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
            System.out.println("Request for " + partName + " and returned a price of " + numberFormat.format(price));
            clientSocket.close();
            System.out.println("Client Connection Terminated");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Worker Thread Terminated");
    }
}
