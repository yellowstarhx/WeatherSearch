package com.example.weatherapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.weatherapp.R;
import com.example.weatherapp.activities.DetailedWeatherActivity;
import com.example.weatherapp.adaptors.DailyWeatherAdapter;
import com.example.weatherapp.appDataHandler.DetailedWeather;
import com.example.weatherapp.appDataHandler.LocationInfo;
import com.example.weatherapp.appDataHandler.WeatherParser;

import org.json.JSONObject;

import java.util.List;


public class SummaryFragment extends Fragment {

    private DetailedWeather detailedWeather;
    private List<WeatherParser.DailyWeather> dailyWeathers;
    private LocationInfo locationInfo;
    private Activity activity;
    private View view;

    public SummaryFragment(Activity activity, LocationInfo locationInfo) {
        this.activity = activity;
        this.locationInfo = locationInfo;
    }

    public SummaryFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (Activity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_summary, container, false);
        if (activity != null && locationInfo != null) {
            getWeather();
        }
        return view;
    }

    public void setLocationInfo(Activity activity, LocationInfo locationInfo) {
        Log.d("Summary Fragment", "starting set LocationInfo");
        this.activity = activity;
        this.locationInfo = locationInfo;
        getWeather();
    }

//    public void setLocationInfo(LocationInfo locationInfo) {
//        Log.d("Summary Fragment", "starting set LocationInfo");
//        this.locationInfo = locationInfo;
//        getWeather();
//    }

    private void setSummaryUI() {
        Log.d("Summary Fragment", "starting set UI");
        // First card
        TextView temperature = view.findViewById(R.id.summaryTemperature);
        TextView summary = view.findViewById(R.id.summaryWeather);
        TextView city = view.findViewById(R.id.summaryCity);
        ImageView icon = view.findViewById(R.id.summaryIcon);

        temperature.setText(detailedWeather.getTemperature());
        summary.setText(detailedWeather.getSummary());
        city.setText(locationInfo.getAddress());
        icon.setImageResource(detailedWeather.getIcon());

        // Second Card
        TextView humidity = view.findViewById(R.id.humidityData);
        TextView windSpeed = view.findViewById(R.id.windSpeedData);
        TextView visibility = view.findViewById(R.id.visibilityData);
        TextView pressure = view.findViewById(R.id.pressureData);

        humidity.setText(detailedWeather.getHumidity());
        windSpeed.setText(detailedWeather.getWindSpeed());
        visibility.setText(detailedWeather.getVisibility());
        pressure.setText(detailedWeather.getPressure());

        // Third card
        RecyclerView recyclerView = view.findViewById(R.id.nextWeek);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        DailyWeatherAdapter adapter = new DailyWeatherAdapter(dailyWeathers);
        recyclerView.setAdapter(adapter);

        // click on 1st card
        CardView summaryCard = view.findViewById(R.id.cardToday);
        summaryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, DetailedWeatherActivity.class);
                intent.putExtra("DetailedWeather", detailedWeather);
                startActivity(intent);
            }
        });

        Log.d("Summary Fragment", "UI has been set");
    }

    private void getWeather() {
        Log.d("Summary Fragment", "starting requiring weather");
        WeatherParser.forecast(activity.getApplicationContext(), locationInfo,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        WeatherParser weatherParser = new WeatherParser(response);
                        Log.d("WeatherParser", response.toString());
                        detailedWeather = new DetailedWeather(weatherParser);
                        dailyWeathers = weatherParser.weeklyWeather();
                        setSummaryUI();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("ERROR", error.toString());
                    }
                });
    }

}
