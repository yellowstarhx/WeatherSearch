package com.example.weatherapp.appDataHandler;

import java.io.Serializable;

public class DetailedWeather implements Serializable {

    // today
    private String windSpeed;
    private String pressure;
    private String precipitation;
    private String temperature;
    private String summary;
    private int icon;
    private String humidity;
    private String visibility;
    private String cloudCover;
    private String ozone;

    // weekly
    private double[] weeklyHighTempArr;
    private double[] weeklyLowTempArr;
    private String weeklySummary;
    private int weeklyIcon;

    private String city;

    public DetailedWeather (WeatherParser weatherParser) {
        city = weatherParser.getCity();

        windSpeed = weatherParser.getCurrentWindSpeed();
        pressure = weatherParser.getCurrentPressure();
        humidity = weatherParser.getCurrentHumidity();
        visibility = weatherParser.getCurrentVisibility();
        temperature = weatherParser.getSummaryTemperature();
        summary = weatherParser.getSummaryWeather();
        icon = weatherParser.getSummaryIcon();
        precipitation = weatherParser.getCurrentPrecipitation();
        cloudCover = weatherParser.getCurrentCloudCover();
        ozone = weatherParser.getCurrentOzone();

        weeklyHighTempArr = weatherParser.getWeeklyDataArray("temperatureHigh");
        weeklyLowTempArr = weatherParser.getWeeklyDataArray("temperatureLow");
        weeklySummary = weatherParser.getWeeklySummary();
        weeklyIcon = weatherParser.getWeeklySummaryIcon();
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getPressure() {
        return pressure;
    }

    public String getPrecipitation() {
        return precipitation;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getSummary() {
        return summary;
    }

    public int getIcon() {
        return icon;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getCloudCover() {
        return cloudCover;
    }

    public String getOzone() {
        return ozone;
    }

    public double[] getHighArr() {
        return weeklyHighTempArr;
    }

    public double[] getLowArr() {
        return weeklyLowTempArr;
    }

    public String getCity() {
        return city;
    }

    public String getWeeklySummary() {
        return weeklySummary;
    }

    public int getWeeklyIcon() {
        return weeklyIcon;
    }
}
