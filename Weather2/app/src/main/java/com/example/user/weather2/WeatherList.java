package com.example.user.weather2;

import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;




public final class WeatherList {


     //************************************






    public static final String WEATHER_CATEGORY[] = {
            "Home",
            "Trip",
    };



    public static List<Weather> list;

    public static List<Weather> setupWeather(double[] temperatures,double[] temperatures_min,double[] temperatures_max,String[] dateAsString,String[] weatherIcons) {

        //call remote fetch data








//((int) temperatures[1])

        list = new ArrayList<Weather>();
        list.add(buildWeatherInfo("Today",((int) temperatures[0]),((int) temperatures_min[0]),((int) temperatures_max[0]),35,weatherIcons[0]));//"http://runitlikeamom.com/wp-content/uploads/2016/03/Sunshine-and-Rainbows-300x199.jpg"));
        list.add(buildWeatherInfo(dateAsString[1],((int) temperatures[1]),((int) temperatures_min[1]),((int) temperatures_max[1]),40,weatherIcons[1]));//"http://assets1.smoothradio.com/2013/30/weather-1375260252-hero-wide-1.jpg"));
        list.add(buildWeatherInfo(dateAsString[2],((int) temperatures[2]),((int) temperatures_min[2]),((int) temperatures_max[2]),25,weatherIcons[2]));//"http://cdn-03.independent.ie/incoming/article34676039.ece/27a86/AUTOCROP/h342/Sunshine.jpg"));
        list.add(buildWeatherInfo(dateAsString[3],((int) temperatures[3]),((int) temperatures_min[3]),((int) temperatures_max[3]),80,weatherIcons[3]));//"https://www.neefusa.org/sites/default/files/styles/hero_block/public/homepage/WEB15-Weather-UdemyCourseExtremeWeather-Hero-3840x2160.jpg?itok=60bHmaWn"));
        list.add(buildWeatherInfo(dateAsString[4],((int) temperatures[4]),((int) temperatures_min[4]),((int) temperatures_max[4]),35,weatherIcons[4]));//"https://lintvwkbn.files.wordpress.com/2016/03/youngstown-ohio-weather-forecast-sunny-breezy.jpg?w=350&h=197&crop=1"));

        return list;
    }

    private static Weather buildWeatherInfo(String day, int temp, int temp_min,int temp_max, int humidity, String cardImageUrl) {
        Weather weather = new Weather();
        weather.setId(Weather.getCount());
        Weather.incCount();
        weather.setTemp(temp);
        weather.setTemp_min(temp_min);
        weather.setTemp_max(temp_max);
        weather.setHumidity(humidity);
        weather.setCardImageUrl(cardImageUrl);
        weather.setDay(day);

        return weather;
    }






}
