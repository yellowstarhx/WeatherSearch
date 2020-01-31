package com.example.weatherapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.activities.DetailedWeatherActivity;
import com.example.weatherapp.appDataHandler.DetailedWeather;

public class TodayFragment extends Fragment {

    public static final String title = "Today";

    private DetailedWeatherActivity detailedWeatherActivity;
    private DetailedWeather detailedWeather;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailedWeatherActivity = (DetailedWeatherActivity) getActivity();
        detailedWeather = detailedWeatherActivity.getDetailedWeather();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        setView(view);
        return view;
    }

    private void setView(View view) {
        ImageView icon = view.findViewById(R.id.centerIcon);
        TextView summary = view.findViewById(R.id.detail_today_center_data);
        icon.setImageResource(detailedWeather.getIcon());
        summary.setText(detailedWeather.getSummary());

        TextView windSpeedTextView = view.findViewById(R.id.detail_today_leftTop_data);
        windSpeedTextView.setText(detailedWeather.getWindSpeed());

        TextView pressureTextView = view.findViewById(R.id.detail_today_midTop_data);
        pressureTextView.setText(detailedWeather.getPressure());

        TextView precipitationTextView = view.findViewById(R.id.detail_today_rightTop_data);
        precipitationTextView.setText(detailedWeather.getPrecipitation());

        TextView temperatureTextView = view.findViewById(R.id.detail_today_leftMid_data);
        temperatureTextView.setText(detailedWeather.getTemperature());

        TextView humidityTextView = view.findViewById(R.id.detail_today_rightMid_data);
        humidityTextView.setText(detailedWeather.getHumidity());

        TextView visibilityTextView = view.findViewById(R.id.detail_today_leftBottom_data);
        visibilityTextView.setText(detailedWeather.getVisibility());

        TextView cloudCoverTextView = view.findViewById(R.id.detail_today_midBottom_data);
        cloudCoverTextView.setText(detailedWeather.getCloudCover());

        TextView ozoneTextView = view.findViewById(R.id.detail_today_rightBottom_data);
        ozoneTextView.setText(detailedWeather.getOzone());
    }

}
