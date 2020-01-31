package com.example.weatherapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.R;
import com.example.weatherapp.adaptors.DailyWeatherAdapter;
import com.example.weatherapp.adaptors.SummaryViewPagerAdapter;
import com.example.weatherapp.appDataHandler.DetailedWeather;
import com.example.weatherapp.appDataHandler.LocationHandler;
import com.example.weatherapp.appDataHandler.LocationInfo;
import com.example.weatherapp.appDataHandler.WeatherParser;
import com.example.weatherapp.fragments.SummaryFragment;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private SearchView searchView;
    private LinearLayout linlaHeaderProgress;
    private SummaryViewPagerAdapter adapter;
    private ViewPager viewPager;
    private LocationInfo currentLocationInfo;
    private Activity mActivity;

    private LocationHandler currentLoc;
    private WeatherParser localWeatherParser;

//    private ProgressBar mProgressBar;
    private Toolbar myToolbar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_bar, menu);

        myToolbar.setBackgroundColor(Color.BLACK);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search_button).getActionView();

//        searchView.onActionViewExpanded();

        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true); // false: iconify the widget; expand it by default

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String input) {
                Log.d("SearchView", "TextSubmit : " + input);
                Intent intent = new Intent(getApplicationContext(), SearchableActivity.class);
                intent.putExtra(SearchManager.QUERY, input);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String input) {
                Log.d("SearchView", "TextChange --> " + input);
                return false;
            }

        });

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivity = this;
        myToolbar = (Toolbar) findViewById(R.id.summary_toolbar);
        setSupportActionBar(myToolbar);

//        Debug.waitForDebugger();

//        getInfoAndDisplay();

        linlaHeaderProgress = findViewById(R.id.linlaHeaderProgress);

        setViewpager();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (searchView != null) {
            searchView.setIconified(true);
            searchView.onActionViewCollapsed();
        }

        setViewpager();
    }

    private void setViewpager() {

        linlaHeaderProgress.setVisibility(View.VISIBLE);
        adapter = new SummaryViewPagerAdapter(getSupportFragmentManager());
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, LocationHandler.IP_API, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            double lat = response.getDouble("lat");
                            double lon = response.getDouble("lon");
                            String address = response.getString("city") + ", " + response.getString("region") + ", " + response.getString("countryCode");

                            currentLocationInfo = new LocationInfo(lat, lon, address);
                            adapter.add(new SummaryFragment(mActivity, currentLocationInfo));

                            SharedPreferences  mPrefs = getPreferences(MODE_PRIVATE);
                            Map<String, ?> map = mPrefs.getAll();
                            Log.d("map length", map.size() + "");
                            for (Map.Entry<String, ?> entry : map.entrySet()) {
                                Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
                                LocationInfo locationInfo = new Gson().fromJson((String) entry.getValue(), LocationInfo.class);
                                adapter.add(new SummaryFragment(mActivity, locationInfo));
                            }

                            viewPager = (ViewPager) findViewById(R.id.summary_viewpager);
                            viewPager.setAdapter(adapter);
                            linlaHeaderProgress.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            Log.e("JSON Exception", "Error get location by IP");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", "Error get location by IP");
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);

    }

//    private void setUI(final WeatherParser weatherParser) {
//        // First card
//        TextView temperature = findViewById(R.id.summaryTemperature);
//        TextView summary = findViewById(R.id.summaryWeather);
//        TextView city = findViewById(R.id.summaryCity);
//        ImageView icon = findViewById(R.id.summaryIcon);
//
//        temperature.setText(weatherParser.getSummaryTemperature());
//        summary.setText(weatherParser.getSummaryWeather());
//        city.setText(weatherParser.getCity());
//        icon.setImageResource(weatherParser.getSummaryIcon());
//
//        // Second Card
//        TextView humidity = findViewById(R.id.humidityData);
//        TextView windSpeed = findViewById(R.id.windSpeedData);
//        TextView visibility = findViewById(R.id.visibilityData);
//        TextView pressure = findViewById(R.id.pressureData);
//
//        humidity.setText(weatherParser.getCurrentHumidity());
//        windSpeed.setText(weatherParser.getCurrentWindSpeed());
//        visibility.setText(weatherParser.getCurrentVisibility());
//        pressure.setText(weatherParser.getCurrentPressure());
//
//        // Third card
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.nextWeek);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        DailyWeatherAdapter adapter = new DailyWeatherAdapter(weatherParser.weeklyWeather());
//        recyclerView.setAdapter(adapter);
//
//        // click on 1st card
//        CardView summaryCard = findViewById(R.id.cardToday);
//        summaryCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, DetailedWeatherActivity.class);
//                DetailedWeather detailedWeather = new DetailedWeather(weatherParser);
//                intent.putExtra("DetailedWeather", detailedWeather);
//                intent.putExtra("address", detailedWeather.getCity());
//                startActivity(intent);
//            }
//        });
//
//        // hide progressBar
////        mProgressBar.setVisibility(View.GONE);
//    }
//
//    private void getInfoAndDisplay() {
//
//        // Step 1 get current location
//        // Step 2 get weatherParser
//        // Step 3 display -- call setUI（）inside getWeather
//
//        // try to use System location
//        if (currentLoc == null) {
//            currentLoc = LocationHandler.getCurrentLocation(this);
//        }
//        // try to use IP API
//        if (currentLoc == null) {
//            getLocationUsingIPAPI();
//        } else {
//            // get Weeather
//            getWeather(currentLoc);
//        }
//
//    }
//
//    private void getLocationUsingIPAPI() {
//        // Instantiate the RequestQueue.
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//        // Request a string response from the provided URL.
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                (Request.Method.GET, LocationHandler.IP_API, null, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            double lat = response.getDouble("lat");
//                            double lon = response.getDouble("lon");
//
//                            currentLoc = new LocationHandler(lat, lon);
//                            getWeather(currentLoc);
//
//                        } catch (JSONException e) {
//                            Log.e("JSON Exception", "Error get location by IP");
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("VolleyError", "Error get location by IP");
//                    }
//                });
//
//        // Add the request to the RequestQueue.
//        queue.add(jsonObjectRequest);
//    }
//
//    private void getWeather(LocationHandler locationHandler) {
//        WeatherParser.forecast(this, locationHandler,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        WeatherParser weatherParser = new WeatherParser(response);
//                        localWeatherParser = weatherParser;
//                        Log.d("WeatherParser", response.toString());
//                        setUI(weatherParser);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.wtf("ERROR", error.toString());
//                    }
//                });
//    }
}
