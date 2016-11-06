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

import java.lang.reflect.Array;
import java.net.URI;
import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainFragment extends BrowseFragment {
    private static final String TAG = "MainFragment";

    private static final int BACKGROUND_UPDATE_DELAY = 300;
    private static final int GRID_ITEM_WIDTH = 200;
    private static final int GRID_ITEM_HEIGHT = 200;
    private static final int NUM_ROWS = 2;
    private static final int NUM_COLS = 5;

    private final Handler mHandler = new Handler();
    private final Handler nHandler = new Handler();
    private ArrayObjectAdapter mRowsAdapter;
    private Drawable mDefaultBackground;
    private DisplayMetrics mMetrics;
    private Timer mBackgroundTimer;
    private URI mBackgroundURI;
    private BackgroundManager mBackgroundManager;


    //****************************
    public String cityField;
    public long[] dates;
    public int[] humid;
    public double[] temperatures;
    public double[] temperatures_min;
    public double[] temperatures_max;
    public int[] weatherId;
    public String[] weatherIcons;
    public String[] dateAsString;
    public JSONObject json1;




    //**********************************
    public void renderWeather(JSONObject json){


        try {
            cityField=(json.getJSONObject("city").getString("name").toUpperCase(Locale.US) +
                    ", " +
                    json.getJSONObject("city").getString("country"));
            JSONArray daysArray = json.getJSONArray("list");
            JSONObject day1Object = daysArray.getJSONObject(0);
            JSONObject day2Object = daysArray.getJSONObject(1);
            JSONObject day3Object = daysArray.getJSONObject(2);
            JSONObject day4Object = daysArray.getJSONObject(3);
            JSONObject day5Object = daysArray.getJSONObject(4);

            temperatures = new double[]{day1Object.getJSONObject("temp").getDouble("day"),
                    day2Object.getJSONObject("temp").getDouble("day"),
                    day3Object.getJSONObject("temp").getDouble("day"),
                    day4Object.getJSONObject("temp").getDouble("day"),
                    day5Object.getJSONObject("temp").getDouble("day")};

            temperatures_min = new double[]{day1Object.getJSONObject("temp").getDouble("min"),
                    day2Object.getJSONObject("temp").getDouble("min"),
                    day3Object.getJSONObject("temp").getDouble("min"),
                    day4Object.getJSONObject("temp").getDouble("min"),
                    day5Object.getJSONObject("temp").getDouble("min")};

            temperatures_max = new double[]{day1Object.getJSONObject("temp").getDouble("max"),
                    day2Object.getJSONObject("temp").getDouble("max"),
                    day3Object.getJSONObject("temp").getDouble("max"),
                    day4Object.getJSONObject("temp").getDouble("max"),
                    day5Object.getJSONObject("temp").getDouble("max")};

            dates=new long[]{day1Object.getLong("dt"),day2Object.getLong("dt"),day3Object.getLong("dt")
                    ,day4Object.getLong("dt"),day5Object.getLong("dt")};

            humid =new int[]{day1Object.getInt("humidity"),day2Object.getInt("humidity"),
                    day3Object.getInt("humidity"),day4Object.getInt("humidity"),day5Object.getInt("humidity")};

            weatherId = new int[]{day1Object.getJSONArray("weather").getJSONObject(0).getInt("id"),
                    day2Object.getJSONArray("weather").getJSONObject(0).getInt("id"),
                    day3Object.getJSONArray("weather").getJSONObject(0).getInt("id"),
                    day4Object.getJSONArray("weather").getJSONObject(0).getInt("id"),
                    day5Object.getJSONArray("weather").getJSONObject(0).getInt("id")};

            DateFormat df = DateFormat.getDateTimeInstance();
            dateAsString = new String[]{df.format(new Date(day1Object.getLong("dt")*1000)),df.format(new Date(day2Object.getLong("dt")*1000)),
                    df.format(new Date(day3Object.getLong("dt")*1000)),df.format(new Date(day4Object.getLong("dt")*1000)),df.format(new Date(day5Object.getLong("dt")*1000))};
            weatherIcons = new String[]{setWeatherIcon(weatherId[0]),setWeatherIcon(weatherId[1]),setWeatherIcon(weatherId[2]),setWeatherIcon(weatherId[3]),setWeatherIcon(weatherId[4])};

           

            //*******


        }catch(Exception e){
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }

    }

    private String setWeatherIcon(int actualId){
        int id = actualId / 100;
        String icon = "";
        if(actualId == 800){
            icon = "http://runitlikeamom.com/wp-content/uploads/2016/03/Sunshine-and-Rainbows-300x199.jpg"; //clear sky
        } else {
            switch(id) {
                case 2 : icon = "https://www.neefusa.org/sites/default/files/styles/hero_block/public/homepage/WEB15-Weather-UdemyCourseExtremeWeather-Hero-3840x2160.jpg?itok=60bHmaWn"; //thunder
                    break;
                case 3 : icon = "https://media.rivermedia.ie/YToyOntzOjQ6ImRhdGEiO3M6MjQ0OiJhOjQ6e3M6MzoidXJsIjtzOjExMjoiaHR0cDovL3MzLWV1LXdlc3QtMS5hbWF6b25hd3MuY29tL3N0b3JhZ2UucHVibGlzaGVycGx1cy5pZS9tZWRpYS5yaXZlcm1lZGlhLmllL3VwbG9hZHMvMjAxNS8xMC8wNjA3MzIzNS9NaXN0LmpwZyI7czo1OiJ3aWR0aCI7aTo2NDA7czo2OiJoZWlnaHQiO2k6MzczO3M6NzoiZGVmYXVsdCI7czo0ODoiaHR0cHM6Ly93d3cuZG9uZWdhbG5vdy5jb20vYXNzZXRzL2kvbm8taW1hZ2UucG5nIjt9IjtzOjQ6Imhhc2giO3M6NDA6ImYxMjJiNTJmMWMyNWJmOGIzODIxZWNhYzUxZGQ2YjgxZTRkM2M3OWYiO30=/weather-cloudy-and-misty-with-drizzle.jpg"; //drizzle
                    break;
                case 7 : icon = "http://i.telegraph.co.uk/multimedia/archive/02061/leicester-park_2061557i.jpg"; //foggy
                    break;
                case 8 : icon = "http://photovide.com/wp-content/uploads/2012/10/Cloudy-Weather-01.jpg";
                    break;
                case 6 : icon = "http://msnbcmedia.msn.com/j/MSNBC/Components/Photo/_new/130325-snow-maryland-hlarge-930a.380;380;7;70;0.jpg"; //snowy
                    break;
                case 5 : icon = "http://i1.manchestereveningnews.co.uk/incoming/article783233.ece/ALTERNATES/s615/C_71_article_1313868_image_list_image_list_item_4_image.jpg"; //rainy
                    break;
            }
        }

        return icon;}


    //****************

    private void updateWeatherData(final String city){
        new Thread(){
            public void run(){
                final JSONObject json = RemoteFetch.getJSON(getActivity(), city);
                if(json == null){
                    nHandler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(getActivity(),
                                    getActivity().getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    nHandler.post(new Runnable(){
                        public void run(){
                            renderWeather(json);
                            loadRows();
                        }
                    });
                }
            }

        }.start();


    }




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onActivityCreated(savedInstanceState);



        //******************



       // prepareBackgroundManager();


        setupUIElements();
        updateWeatherData("Athens,GR");


      //  setupEventListeners();




    }







    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mBackgroundTimer) {
            Log.d(TAG, "onDestroy: " + mBackgroundTimer.toString());
            mBackgroundTimer.cancel();
        }
    }

    private void loadRows() {
        //List<Movie> list = MovieList.setupMovies();
        List<Weather> list1 = WeatherList.setupWeather(temperatures,temperatures_min,temperatures_max,dateAsString,weatherIcons);

        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        CardPresenter cardPresenter = new CardPresenter();

        int i;
        for (i = 0; i < NUM_ROWS; i++) {
           // if (i != 0) {
           //     Collections.shuffle(list1);
           // }
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
            for (int j = 0; j < NUM_COLS; j++) {
                listRowAdapter.add(list1.get(j));
            }
            HeaderItem header = new HeaderItem(i, WeatherList.WEATHER_CATEGORY[i]);
            mRowsAdapter.add(new ListRow(header, listRowAdapter));
        }

        HeaderItem gridHeader = new HeaderItem(i, "PREFERENCES");

        GridItemPresenter mGridPresenter = new GridItemPresenter();
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);
        gridRowAdapter.add(getResources().getString(R.string.grid_view));
        gridRowAdapter.add(getString(R.string.error_fragment));
        gridRowAdapter.add(getResources().getString(R.string.personal_settings));
        mRowsAdapter.add(new ListRow(gridHeader, gridRowAdapter));

        setAdapter(mRowsAdapter);

    }

    private void prepareBackgroundManager() {

        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());
        mDefaultBackground = getResources().getDrawable(R.drawable.default_background);
        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    private void setupUIElements() {
        // setBadgeDrawable(getActivity().getResources().getDrawable(
        // R.drawable.videos_by_google_banner));
        setTitle(getString(R.string.browse_title)); // Badge, when set, takes precedent
        // over title
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);

        // set fastLane (or headers) background color
        setBrandColor(getResources().getColor(R.color.fastlane_background));
        // set search icon color
        setSearchAffordanceColor(getResources().getColor(R.color.search_opaque));
    }

    private void setupEventListeners() {
        setOnSearchClickedListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Implement your own in-app search", Toast.LENGTH_LONG)
                        .show();
            }
        });

        setOnItemViewClickedListener(new ItemViewClickedListener());
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
    }

    protected void updateBackground(String uri) {
        int width = mMetrics.widthPixels;
        int height = mMetrics.heightPixels;
        Glide.with(getActivity())
                .load(uri)
                .centerCrop()
                .error(mDefaultBackground)
                .into(new SimpleTarget<GlideDrawable>(width, height) {
                    @Override
                    public void onResourceReady(GlideDrawable resource,
                                                GlideAnimation<? super GlideDrawable>
                                                        glideAnimation) {
                        mBackgroundManager.setDrawable(resource);
                    }
                });
        mBackgroundTimer.cancel();
    }

    private void startBackgroundTimer() {
        if (null != mBackgroundTimer) {
            mBackgroundTimer.cancel();
        }
        mBackgroundTimer = new Timer();
        mBackgroundTimer.schedule(new UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY);
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {

            if (item instanceof Movie) {
                Movie movie = (Movie) item;
                Log.d(TAG, "Item: " + item.toString());
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.MOVIE, movie);

                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        ((ImageCardView) itemViewHolder.view).getMainImageView(),
                        DetailsActivity.SHARED_ELEMENT_NAME).toBundle();
                getActivity().startActivity(intent, bundle);
            } else if (item instanceof String) {
                if (((String) item).indexOf(getString(R.string.error_fragment)) >= 0) {
                    Intent intent = new Intent(getActivity(), BrowseErrorActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), ((String) item), Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }
    }

    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {
        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                                   RowPresenter.ViewHolder rowViewHolder, Row row) {
            if (item instanceof Movie) {
                mBackgroundURI = ((Movie) item).getBackgroundImageURI();
                startBackgroundTimer();
            }

        }
    }

    private class UpdateBackgroundTask extends TimerTask {

        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mBackgroundURI != null) {
                        updateBackground(mBackgroundURI.toString());
                    }
                }
            });

        }
    }

    private class GridItemPresenter extends Presenter {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent) {
            TextView view = new TextView(parent.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT));
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.setBackgroundColor(getResources().getColor(R.color.default_background));
            view.setTextColor(Color.WHITE);
            view.setGravity(Gravity.CENTER);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, Object item) {
            ((TextView) viewHolder.view).setText((String) item);
        }

        @Override
        public void onUnbindViewHolder(ViewHolder viewHolder) {
        }
    }

}
