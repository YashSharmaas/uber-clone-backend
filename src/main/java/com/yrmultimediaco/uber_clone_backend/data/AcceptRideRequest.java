package com.yrmultimediaco.uber_clone_backend.data;


public class AcceptRideRequest {
    private String rideId;
    private String driverId;
    private String riderId;

    // Constructors
    public AcceptRideRequest() {}

    public AcceptRideRequest(String rideId, String driverId, String riderId) {
        this.rideId = rideId;
        this.driverId = driverId;
        this.riderId = riderId;
    }

    // Getters and Setters
    public String getRideId() { return rideId; }
    public void setRideId(String rideId) { this.rideId = rideId; }

    public String getDriverId() { return driverId; }
    public void setDriverId(String driverId) { this.driverId = driverId; }

    public String getRiderId() { return riderId; }
    public void setRiderId(String riderId) { this.riderId = riderId; }
}
