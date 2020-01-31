package com.example.weatherapp.appDataHandler;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.R;
import com.example.weatherapp.external.ServerAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class WeatherParser implements Serializable {

    private static final Map<String, Integer> iconMap = initIconMap();

    private static Map<String, Integer> initIconMap() {
        Map<String, Integer> iconMap = new HashMap<>();

        iconMap.put("clear-day", R.drawable.weather_sunny);
        iconMap.put("clear-night", R.drawable.weather_night);
        iconMap.put("rain", R.drawable.weather_rainy);
        iconMap.put("snow", R.drawable.weather_snowy);
        iconMap.put("sleet", R.drawable.weather_snowy_rainy);
        iconMap.put("wind", R.drawable.weather_windy_variant);
        iconMap.put("fog", R.drawable.weather_fog);
        iconMap.put("cloudy", R.drawable.weather_cloudy);
        iconMap.put("partly-cloudy-day", R.drawable.weather_partly_cloudy);
        iconMap.put("partly-cloudy-night", R.drawable.weather_night_partly_cloudy);

        return iconMap;
    }

    private JSONObject forecastResult;

    public WeatherParser(JSONObject forecastResult) {
        this.forecastResult = forecastResult;
    }


    public static void forecast(Context context, LocationInfo locationInfo,
                                Response.Listener<JSONObject> successCallback,
                                Response.ErrorListener errorCallback) {

        String url = String.format(ServerAPI.FORECAST, locationInfo.getLat(), locationInfo.getLon());

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);

        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, successCallback, errorCallback);

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    // card 1 today
    private Double summaryTemperature;
    private String summaryWeather;
    private String timeZone;
    private String city;

    public String getSummaryTemperature() {
        if (summaryTemperature != null) {
            return Math.round(summaryTemperature) + "°F";
        }
        try {
            summaryTemperature = forecastResult.getJSONObject("currently").getDouble("temperature");
        } catch (JSONException e) {
            Log.e("JSON Exception", "Parsing summaryTemperature");
        }

        return Math.round(summaryTemperature) + "°F";
    }

    public String getSummaryWeather() {
        if (summaryWeather != null) {
            return summaryWeather;
        }
        try {
            summaryWeather = forecastResult.getJSONObject("currently").getString("summary");
        } catch (JSONException e) {
            Log.e("JSON Exception", "Parsing summaryWeather");
        }
        return summaryWeather;
    }

    public String getTimeZone() {
        if (timeZone != null) {
            return timeZone;
        }
        try {
            timeZone = forecastResult.getString("timezone");
        } catch (JSONException e) {
            Log.e("JSON Exception", "Parsing Time Zone");
        }
        return timeZone;
    }

    public String getCity() {
        if (city != null) {
            return city;
        }
        if (timeZone == null) {
            getTimeZone();
        }
        city = timeZone.substring(timeZone.indexOf('/') + 1);
        city = city.replace('_', ' ');
        return city;
    }

    public int getSummaryIcon() {
        Integer icon = null;
        try {
            icon = iconMap.get(forecastResult.getJSONObject("currently").getString("icon"));
        } catch (JSONException e) {
            Log.e("JSON Exception", "Parsing City");
            icon = iconMap.get("clear-day");
        }
        return icon;

    }

    // card 2
    private Double currentHumidity;
    private Double currentWindSpeed;
    private Double currentVisibility;
    private Double currentPressure;

    public String getCurrentHumidity() {
        if (currentHumidity != null) {
            return Math.round(currentHumidity * 100) + "%";
        }
        try {
            currentHumidity = forecastResult.getJSONObject("currently").getDouble("humidity");
        } catch (JSONException e) {
            Log.e("JSON Exception", "Parsing currentHumidity");
        }
        return Math.round(currentHumidity * 100) + "%";
    }

    public String getCurrentWindSpeed() {
        if (currentWindSpeed != null) {
            return currentWindSpeed + " mph";
        }
        try {
            currentWindSpeed = forecastResult.getJSONObject("currently").getDouble("windSpeed");
        } catch (JSONException e) {
            Log.e("JSON Exception", "Parsing currentWindSpeed");
        }
        return currentWindSpeed + " mph";
    }

    public String getCurrentVisibility() {
        if (currentVisibility != null) {
            return currentVisibility + " km";
        }
        try {
            currentVisibility = forecastResult.getJSONObject("currently").getDouble("visibility");
        } catch (JSONException e) {
            Log.e("JSON Exception", "Parsing currentVisibility");
        }
        return currentVisibility + " km";
    }

//    public String getStringValue(String key, String unit) {
//        if (!hashMap.containsKey(value)) {
//            try {
//            hashMap.put(value, forecastResult.getJSONObject("currently")).getString(value));
//            catch
//        }
//        return hashMap.get(value)+ unit;
//    }

    public String getCurrentPressure() {
        if (currentPressure != null) {
            return currentPressure + " mb";
        }
        try {
            currentPressure = forecastResult.getJSONObject("currently").getDouble("pressure");
        } catch (JSONException e) {
            Log.e("JSON Exception", "Parsing currentPressure");
        }
        return currentPressure + " mb";
    }

    // card 3
    private static SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy ");

    public static class DailyWeather implements Serializable {
        private String icon;
        private long time;
        private double low;
        private double high;
        private String TIME_ZONE;

        public DailyWeather (JSONObject forecast, String timezone) {
            TIME_ZONE = timezone;
            try {
                icon = forecast.getString("icon");
                time = forecast.getLong("time");
                low = forecast.getDouble("temperatureLow");
                high = forecast.getDouble("temperatureHigh");
            } catch (JSONException e) {
                Log.e("JSON Exception", "Parsing Daily WeatherParser");
            }
        }

        public int getIconImageView() {
            return iconMap.get(icon);
        }

        public String getHighTextView() {
            return String.valueOf(Math.round(high));
        }

        public String getLowTextView() {
            return String.valueOf(Math.round(low));
        }

        public String getDate() {
            return convertTime(time);
        }

        public String convertTime(long timestamp) {
            Date date = new Date(timestamp * 1000);
            TimeZone timeZone = TimeZone.getTimeZone(TIME_ZONE);
            formatter.setTimeZone(timeZone);
            return formatter.format(date);
        }
    }

    public List<DailyWeather> weeklyWeather() {
        List<DailyWeather> weeklyData = new ArrayList<>();
        try {
            JSONArray week = forecastResult.getJSONObject("daily").getJSONArray("data");
            for(int i = 0; i < week.length(); i++) {
                weeklyData.add(new DailyWeather(week.getJSONObject(i), getTimeZone()));
            }
        } catch (JSONException e) {
            Log.e("JSON Exception", "Parsing Weekly WeatherParser");
        }
        return weeklyData;
    }

    private String currentPrecipitationString;

    public String getCurrentPrecipitation() {
        if (currentPrecipitationString != null) {
            return currentPrecipitationString;
        }
        try {
            double currentPrecipitation = forecastResult.getJSONObject("currently").getDouble("precipProbability");
            currentPrecipitationString = String.format("%.2f", currentPrecipitation) + " mmph";
            return currentPrecipitationString;
        } catch (JSONException e) {
            Log.e("JSON Exception", "Parsing currentPressure");
        }
        return "--.-- mmph";
    }

    private Double currentCloudCover;

    public String getCurrentCloudCover() {
        if (currentCloudCover != null) {
            return Math.round(currentCloudCover * 100) + " %";
        }
        try {
            currentCloudCover = forecastResult.getJSONObject("currently").getDouble("cloudCover");
        } catch (JSONException e) {
            Log.e("JSON Exception", "Parsing currentCloudCover");
        }
        return Math.round(currentCloudCover * 100) + " %";
    }

    private String currentOzoneString;

    public String getCurrentOzone() {
        if (currentOzoneString != null) {
            return currentOzoneString;
        }
        try {
            double currentOzone = forecastResult.getJSONObject("currently").getDouble("ozone");
            Log.d("currentOzone", currentOzone + "");
            Log.d("currentOzone format", String.format("%.2f", currentOzone));
            currentOzoneString = String.format("%.2f", currentOzone) + " DU";
            Log.d("currentOzone String", currentOzoneString);
            return currentOzoneString;
        } catch (JSONException e) {
            Log.e("JSON Exception", "Parsing currentOzone");
        }
        return "-.-- mmph";
    }

    private String weeklySummary;

    public String getWeeklySummary() {
        if (weeklySummary != null) {
            return weeklySummary;
        }
        try {
            weeklySummary = forecastResult.getJSONObject("daily").getString("summary");
        } catch (JSONException e) {
            Log.e("JSON Exception", "Parsing WeeklySummary");
        }
        return weeklySummary;
    }

    public int getWeeklySummaryIcon() {
        try {
            return iconMap.get(forecastResult.getJSONObject("daily").getString("icon"));
        } catch (JSONException e) {
            Log.e("JSON Exception", "Parsing WeeklySummaryIcon");
        }
        return iconMap.get("clear-day");
    }

    public double[] getWeeklyDataArray(String key) {
        try {
            JSONArray weeklyWeather = forecastResult.getJSONObject("daily").getJSONArray("data");
            double[] arr = new double[weeklyWeather.length()];
            for (int i = 0; i < arr.length; i++) {
                JSONObject dayWeather = (JSONObject) weeklyWeather.get(i);
                arr[i] = dayWeather.getDouble(key);
            }
            return arr;
        } catch (JSONException e) {
            Log.e("JSON Exception", "Parsing weekly " + key);
        }
        return new double[0];
    }

    // test only
    public static void forecast(Context context, LocationHandler locationHandler,
                                final ProgressBar bar, final Button button) {
        forecast(context, locationHandler,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        bar.setVisibility(View.INVISIBLE);
                        button.setText("GOT");
                        Log.d("WeatherParser", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("ERROR", error.toString());
                    }});
    }


    // Legacy code
    public static final String FORECAST_API = "http://NodeJS-CSCI571.us-west-1.elasticbeanstalk.com/forecast";

    static public void forecast(Context context, LocationHandler locationHandler,
                                Response.Listener<JSONObject> successCallback,
                                Response.ErrorListener errorCallback) {
        double lat = locationHandler.getLat();
        double lon = locationHandler.getLon();
        String url = FORECAST_API + "?lat=" + lat + "&lon=" + lon;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);

        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, successCallback, errorCallback);

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }
}
