package com.andela.exchanger.remote;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHelper {// lemme send it to you

    private HttpURLConnection connection;

    private BufferedReader reader;

    public String fetchData(String urlString) {
        String data = "";
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line)
                        .append("\n");
            }
            data = stringBuilder.toString();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.disconnect();
                reader.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return data;
    }


}
