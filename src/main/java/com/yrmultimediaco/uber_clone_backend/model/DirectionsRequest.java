package com.yrmultimediaco.uber_clone_backend.model;


public class DirectionsRequest {
    private String origin;
    private String destination;

    public DirectionsRequest() {}

    // Constructor
    public DirectionsRequest(String origin, String destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
}
