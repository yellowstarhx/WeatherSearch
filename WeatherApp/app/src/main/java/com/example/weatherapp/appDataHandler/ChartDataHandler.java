package com.example.weatherapp.appDataHandler;

import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class ChartDataHandler {

    public static List<Entry> arrayToList(double[] array) {
        List<Entry> list = new ArrayList<>();

        for (int i = 0; i < array.length; i++) {
            list.add(new Entry((float)i, (float)array[i]));
        }

        return list;
    }

    public static LineDataSet loadData(double[] data, String label) {
        List<Entry> dataList = arrayToList(data);
        Log.d("LineChartList Length", dataList.size() + "");
        Log.d("LineChartList data", dataList.toString());
        LineDataSet dataSet = new LineDataSet(dataList, label);
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        return dataSet;
    }
}
