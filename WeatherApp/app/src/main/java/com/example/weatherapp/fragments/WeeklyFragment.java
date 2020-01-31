package com.example.weatherapp.fragments;

import android.graphics.Color;
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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

import java.util.ArrayList;
import java.util.List;

import com.example.weatherapp.appDataHandler.ChartDataHandler;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

public class WeeklyFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_weekly, container, false);

        ImageView icon = view.findViewById(R.id.detail_weekly_summary_icon);
        TextView summary = view.findViewById(R.id.detail_weekly_summary_text);

        icon.setImageResource(detailedWeather.getIcon());
        summary.setText(detailedWeather.getWeeklySummary());

        LineChart lineChart = view.findViewById(R.id.line_chart);
        Legend legend = lineChart.getLegend();
        legend.setTextColor(Color.WHITE);
        legend.setTextSize(14f);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setTextColor(Color.WHITE);
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setDrawAxisLine(true);

        YAxis yAxis = lineChart.getAxisRight();
        yAxis.setTextColor(Color.WHITE);
        yAxis.setDrawAxisLine(true);

        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        LineDataSet highTempSet = ChartDataHandler.loadData(detailedWeather.getHighArr(), "Maximum Temperature");
        highTempSet.setColor(Color.YELLOW);
        highTempSet.setValueTextSize(14f);
        highTempSet.setValueTextColor(Color.WHITE);
        dataSets.add(highTempSet);
        LineDataSet lowTempSet = ChartDataHandler.loadData(detailedWeather.getLowArr(), "Minimum Temperature");
        lowTempSet.setColor(Color.parseColor("#690569"));
        lowTempSet.setValueTextSize(14f);
        lowTempSet.setValueTextColor(Color.WHITE);
        dataSets.add(lowTempSet);

        LineData data = new LineData(dataSets);
        lineChart.setData(data);
        lineChart.invalidate(); // refresh

        return view;
    }

}
