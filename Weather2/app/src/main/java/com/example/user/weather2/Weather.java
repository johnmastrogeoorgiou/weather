/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.user.weather2;

import android.util.Log;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

/*
 * Weather class represents video entity with title, description, image thumbs and video url.
 *
 */
public class Weather implements Serializable {
    private static long count = 0;
    private long id;
    private int temp;
    private int  temp_min;
    private int temp_max;
    private int humidity;
    private String cardImageUrl;
    private String day;

    public Weather() {
    }

    public static long getCount() {
        return count;
    }

    public static void incCount() {
        count++;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp= temp;
    }

    public int getTemp_min() {    return temp_min;}

    public void setTemp_min(int temp_min) {
        this.temp_min= temp_min;
    }

    public int getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(int temp_max) {
        this.temp_max= temp_max;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity= humidity;
    }

    public String getCardImageUrl() {
        return cardImageUrl;
    }

    public void setCardImageUrl(String cardImageUrl) {
        this.cardImageUrl = cardImageUrl;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }


    public URI getCardImageURI() {
        try {
            return new URI(getCardImageUrl());
        } catch (URISyntaxException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", temp='" + temp + '\'' +
                ", temp_max='" + temp_max+ '\'' +
                ", temp_min='" + temp_min+ '\'' +
                ", humidity='" + humidity+ '\'' +

                '}';
    }
}
