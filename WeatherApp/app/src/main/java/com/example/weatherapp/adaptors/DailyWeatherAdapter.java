package com.example.weatherapp.adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.appDataHandler.WeatherParser;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView date;
        TextView high;
        TextView low;

        public ViewHolder(View view) {
            super(view);
            icon = (ImageView) view.findViewById(R.id.dailyIcon);
            date = (TextView) view.findViewById(R.id.dailyDate);
            high = (TextView) view.findViewById(R.id.dailyHigh);
            low = (TextView) view.findViewById(R.id.dailyLow);
        }
    }

    private List<WeatherParser.DailyWeather> weekWeather;

    public DailyWeatherAdapter(List<WeatherParser.DailyWeather> weekWeather) {
        this.weekWeather = weekWeather;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WeatherParser.DailyWeather dayWeather = weekWeather.get(position);
        holder.icon.setImageResource(dayWeather.getIconImageView());
        holder.high.setText(dayWeather.getHighTextView());
        holder.low.setText(dayWeather.getLowTextView());
        holder.date.setText(dayWeather.getDate());
    }

    @Override
    public int getItemCount() {
        return weekWeather.size();
    }
}
