package com.yrmultimediaco.uber_clone_backend.data;

// Driver aur Ride dono ki location store karne ke liye
public class LocationData {
    private double lat;
    private double lng;

    // Constructors, Getters, and Setters...
    public LocationData(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() { return lat; }
    public double getLng() { return lng; }
    public void setLat(double lat) { this.lat = lat; }
    public void setLng(double lng) { this.lng = lng; }
}
