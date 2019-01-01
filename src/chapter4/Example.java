package chapter4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Example {

    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {
        Example example = new Example();
        System.out.println("Sent Http GET request");
        example.sendGet();
    }

    private void sendGet() {
        try {
            String urlQuery = "http://www.google.com/search?q=";
            String userQuery = "java sdk";
            String urlEncoded = urlQuery + URLEncoder.encode(userQuery, "UTF-8");
            System.out.println(urlEncoded);

            String query = "http://www.google.com/search?q=java+sdk&ie=utf-8&oe=utf-8";
            URL url = new URL(query);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = httpURLConnection.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            if (responseCode == 200) {
                String response = getResponse(httpURLConnection);
                System.out.println("response : " + response.toString());
            } else {
                System.out.println("Bad Response Code : " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getResponse(HttpURLConnection httpURLConnection) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {
            System.out.println("Request Headers");
            Map<String, List<String>> requestHeaders = httpURLConnection.getHeaderFields();
            Set<String> set = requestHeaders.keySet();

            for (String key : set) {
                if ("Set-cookie".equals(key)) {
                    List values = requestHeaders.get(key);
                    String cookie = key + " = " + values.toString() + "\n";
                    String cookieName = cookie.substring(0, cookie.indexOf("="));
                    String cookieValue = cookie.substring(cookie.indexOf("=") + 1, cookie.length());
                    System.out.println(cookieName + " : " + cookieValue);
                }
            }
            System.out.println();

            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            bufferedReader.close();
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
