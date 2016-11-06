package com.example.user.weather2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class RemoteFetch {

    private static final String OPEN_WEATHER_MAP_API =
            "http://api.openweathermap.org/data/2.5/forecast/daily?q=Athens,GR&mode=json&units=metric&cnt=5&appid=0df3b84d1bce9a68a79ccc0cdf74083f";

    public static JSONObject getJSON(Context context,String city) {
        try {
            URL url = new URL(OPEN_WEATHER_MAP_API);
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();


            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp = "";
            while ((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            // This value will be 404 if the request was not
            // successful
            if (data.getInt("cod") != 200) {
                Toast.makeText(context,"Error",Toast.LENGTH_LONG);
                return null;
                }

            return data;

        } catch (Exception e) {
            return null;
        }
    }
}