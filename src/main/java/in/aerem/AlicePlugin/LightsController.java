package in.aerem.AlicePlugin;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LightsController {
    public static void switchLight(Boolean on) {
        if (on) {
            executePost("{ \"on\": true }");
        } else {
            executePost("{ \"on\": false }");
        }
    }

    public static String setColor(int red, int green, int blue) {
        int[] hueAndSat = getHueAndSat(red, green, blue);
        String urlParameters = "{ \"hue\": " +
                hueAndSat[0]
                + ", \"sat\": " +
                hueAndSat[1]
                + " }";
        executePost(urlParameters);
        return  urlParameters;

    }

    private static int[] getHueAndSat(int red, int green, int blue) {

        float min = Math.min(Math.min(red, green), blue);
        float max = Math.max(Math.max(red, green), blue);
        float delta = max - min;

        int sat = Math.round(255 * delta / (255 - Math.abs(min + max - 255)));

        if (min == max) {
            return new int[]{0, sat};
        }

        float hue = 0f;
        if (max == red) {
            hue = (green - blue) / (max - min);

        } else if (max == green) {
            hue = 2f + (blue - red) / (max - min);

        } else {
            hue = 4f + (red - green) / (max - min);
        }

        hue = hue * (65536 / 6);
        if (hue < 0) hue = hue + 65536;

        return new int[]{Math.round(hue), sat};
    }

    private  static String executePost(String urlParameters) {
        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection
            url = new URL("http://hue-bridge/api/oWAlM9UyEqI41ABIYvfTXltr5Fu80xTUpwP2lBbg/lights/5/state");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");

            connection.setRequestProperty("Content-Length", "" +
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
