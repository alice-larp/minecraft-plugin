package in.aerem.AlicePlugin;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LightsController {
    private static Gson gson = new Gson();

    //public static void switchLight(Boolean on) {
    //    Map<String, Boolean> m = new HashMap<String, Boolean>();
    //    m.put("on", on);
    //    executePost(gson.toJson(m));
    //}

    public static void setColor(int red, int green, int blue) {
        ColorState s = new ColorState(red, green, blue);
        executePost(gson.toJson(s));
    }

    private static HueState getHueAndSat(int red, int green, int blue) {

        float min = Math.min(Math.min(red, green), blue);
        float max = Math.max(Math.max(red, green), blue);
        float delta = max - min;

        int sat = Math.round(255 * delta / (255 - Math.abs(min + max - 255)));

        if (min == max) {
            return new HueState(0, sat);
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

        return new HueState(Math.round(hue), sat);
    }

    private  static String executePost(String urlParameters) {
        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection
            url = new URL("http://hassbian:1880/bulb/color");
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
