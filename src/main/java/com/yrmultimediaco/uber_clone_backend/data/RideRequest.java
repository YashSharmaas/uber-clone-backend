package com.yrmultimediaco.uber_clone_backend.data;

public class RideRequest {
    private String origin;       // Format: "26.40,80.30"
    private String destination;  // Format: "26.45,80.35"
    private String distanceText; // Format: "5.9 km"
    private String durationText; // Format: "18 mins"
    private double fare;         // Format: 156.36
    private String riderId;
    private String rideId;

    // Default Constructor (Jackson deserialization ke liye zaroori hai)
    public RideRequest() {}

    // Getters and Setters
    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getDistanceText() { return distanceText; }
    public void setDistanceText(String distanceText) { this.distanceText = distanceText; }

    public String getDurationText() { return durationText; }
    public void setDurationText(String durationText) { this.durationText = durationText; }

    public double getFare() { return fare; }
    public void setFare(double fare) { this.fare = fare; }

    public String getRiderId() { return riderId; }
    public void setRiderId(String riderId) { this.riderId = riderId; }

    public String getRideId() { return rideId; }
    public void setRideId(String rideId) { this.rideId = rideId; }
}
