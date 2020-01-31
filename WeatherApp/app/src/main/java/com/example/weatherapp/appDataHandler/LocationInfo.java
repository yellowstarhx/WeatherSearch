package com.example.weatherapp.appDataHandler;

import java.io.Serializable;

public class LocationInfo implements Serializable {
    private String address;
    private double lat;
    private double lon;

    public LocationInfo(double lat, double lon, String address) {
        this.lat = lat;
        this.lon = lon;
        this.address = address;
    }

    private LocationInfo() {}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

}
