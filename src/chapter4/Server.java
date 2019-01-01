package chapter4;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) throws Exception {
        System.out.println("HTTPServer Started");
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(3000), 0);
        httpServer.createContext("/index", new DetailHandler());
        httpServer.setExecutor(Executors.newCachedThreadPool());
        httpServer.setExecutor(Executors.newFixedThreadPool(5));
        httpServer.start();
    }

    public static String getResponse() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("<html><h1>HTTPServer Home Page.... </h1><br>")
                .append("<b>Welcome to the new and improved web " + "server!</b><BR>")
                .append("</html>");
        return stringBuilder.toString();
    }

    static class IndexHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            System.out.println(httpExchange.getRemoteAddress());
            String response = getResponse();
            httpExchange.sendResponseHeaders(200, response.length());

            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();
        }
    }

    static class DetailHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            System.out.println("\nRequest Headers");
            Headers requestHeaders = httpExchange.getRequestHeaders();
            Set<String> set = requestHeaders.keySet();
            for (String key : set) {
                List values = requestHeaders.get(key);
                String header = key + " = " + values.toString() + "\n";
                System.out.print(header);
            }

            String requestMethod = httpExchange.getRequestMethod();
            if (requestMethod.equalsIgnoreCase("GET")) {
                System.out.println("Request Body");
                InputStream inputStream = httpExchange.getRequestBody();
                if (inputStream != null) {
                    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                        String line;
                        StringBuilder stringBuilder = new StringBuilder();
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line);
                        }
                        bufferedReader.close();
                        System.out.println(line);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Request Body is empty");
                }

                Headers responseHeaders = httpExchange.getResponseHeaders();

                String message = getResponse();
                responseHeaders.set("Content-Type", "text/html");
                responseHeaders.set("Server", "MyHTTPServer/1.0");
                responseHeaders.set("Set-cookie", "userID=Cookie Monster");

                httpExchange.sendResponseHeaders(200, message.getBytes().length);
                System.out.println("Response Headers");

                Set<String> responseSet = responseHeaders.keySet();
                responseSet
                        .stream()
                        .map((key) -> {
                            List values = responseHeaders.get(key);
                            String header = key + " = " + values.toString() + "\n";
                            return header;
                        })
                        .forEach((header) -> {
                            System.out.print(header);
                        });
                try (OutputStream outputStream = httpExchange.getResponseBody()) {
                    outputStream.write(message.getBytes());
                }
            }
        }
    }
}
