package com.example.weatherapp.external;

public class TwitterAPI {
    public static final String twitterIntent = "https://twitter.com/intent/tweet?text=Check Out %s 's Weather! It is %s ！ #CSCI571WeatherSearch";

    public static String setTwitterSharedContent(String location, String temperatureString) {
        return String.format(twitterIntent, location, temperatureString);
    }
}
