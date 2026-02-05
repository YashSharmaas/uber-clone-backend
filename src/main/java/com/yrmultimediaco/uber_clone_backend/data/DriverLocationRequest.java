package com.yrmultimediaco.uber_clone_backend.data;

// DriverLocationRequest.java (Backend)
public class DriverLocationRequest {
    private String driverId;
    private String riderId;
    private double lat;
    private double lng;

    public DriverLocationRequest() {}

    public String getRiderId() { return riderId; }
    public void setRiderId(String riderId) { this.riderId = riderId; }

    public String getDriverId() { return driverId; }
    public void setDriverId(String driverId) { this.driverId = driverId; }
    public double getLat() { return lat; }
    public void setLat(double lat) { this.lat = lat; }
    public double getLng() { return lng; }
    public void setLng(double lng) { this.lng = lng; }
}