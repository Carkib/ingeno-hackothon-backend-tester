package ca.ingeno.hackothon.tester.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPRequestSender
{
    public static String getRequestAtUrl(String givenUrl)
    {
        try {
            URL url = new URL(givenUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;

            StringBuilder sb = new StringBuilder();
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }

            conn.disconnect();

            return sb.toString();

        } catch (MalformedURLException e) {
            return "failed";

        } catch (IOException e) {
            return "failed";
        }
    }
}
