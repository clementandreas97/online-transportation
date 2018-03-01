package utility;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestSender {
    public static String sendRequest(String url, String method, String contentType, String data) {

        HttpURLConnection httpURLConnection;
        StringBuilder result = new StringBuilder();
        try {
            URL urlSend = new URL(url);
            httpURLConnection = (HttpURLConnection)urlSend.openConnection();
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content-Type", contentType);

            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            dataOutputStream.writeBytes(data);
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.append("FAILED");
        }
        return result.toString();
    }
}
