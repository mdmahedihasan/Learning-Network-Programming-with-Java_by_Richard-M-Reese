package chapter4;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;

public class ClientHandler implements Runnable {

    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("\nClientHandler Started for : " + this.socket);
        handleRequest(this.socket);
        System.out.println("ClientHandler Terminated for " + this.socket + "\n");
    }

    public void handleRequest(Socket socket) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String headerLine = bufferedReader.readLine();
            StringTokenizer tokenizer = new StringTokenizer(headerLine);
            String httpMethod = tokenizer.nextToken();

            if (httpMethod.equals("GET")) {
                System.out.println("Get Method processed");
                //String httpQueryString = tokenizer.nextToken();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder
                        .append("<html><h1>WebServer Home Page.... </h1><br>")
                        .append("<b>Welcome to my web server!</b><BR>")
                        .append("</html>");
                sendResponse(socket, 200, stringBuilder.toString());
            } else {
                System.out.println("The HTTP method is not recognized");
                sendResponse(socket, 405, "Method Not Allowed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendResponse(Socket socket, int statusCode, String responseString) {
        String statusLine;
        String serverHeader = "Server: WebServer\r\n";
        String contentTypeHeader = "Content-Type: text/html\r\n";

        try (DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())) {
            if (statusCode == 200) {
                statusLine = "HTTP/1.0 200 OK" + "\r\n";
                String contentLengthHeader = "Content-Length: " + responseString.length() + "\r\n";

                dataOutputStream.writeBytes(statusLine);
                dataOutputStream.writeBytes(serverHeader);
                dataOutputStream.writeBytes(contentTypeHeader);
                dataOutputStream.writeBytes(contentLengthHeader);
                dataOutputStream.writeBytes("\r\n");
                dataOutputStream.writeBytes(responseString);
            } else if (statusCode == 405) {
                statusLine = "HTTP/1.0 405 Method Not Allowed" + "\r\n";
                dataOutputStream.writeBytes(statusLine);
                dataOutputStream.writeBytes("\r\n");
            } else {
                statusLine = "HTTP/1.0 404 Not Found" + "\r\n";
                dataOutputStream.writeBytes(statusLine);
                dataOutputStream.writeBytes("\r\n");
            }
            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
