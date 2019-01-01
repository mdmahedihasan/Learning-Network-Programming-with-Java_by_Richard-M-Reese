package chapter4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class HTTPClient {

    public HTTPClient() {
        System.out.println("HTTP Client Started");
        try {
            InetAddress inetAddress = InetAddress.getByName("127.0.0.1");
            Socket socket = new Socket(inetAddress, 3000);

            try (OutputStream outputStream = socket.getOutputStream();
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                sendGet(outputStream);
                System.out.println(getResponse(bufferedReader));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendGet(OutputStream outputStream) {
        try {
            outputStream.write("GET /index HTTP/1.0\r\n".getBytes());
            outputStream.write("User-Agent: Mozilla/5.0\r\n".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getResponse(BufferedReader bufferedReader) {
        try {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputLine).append("\n");
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        new HTTPClient();
    }
}
