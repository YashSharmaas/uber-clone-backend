package com.yrmultimediaco.uber_clone_backend.model;

import com.yrmultimediaco.uber_clone_backend.data.LocationData;
import java.util.UUID;

public class Driver {

    private String driverId; // Firebase Auth UID
    private String name;
    private LocationData currentLocation; // Driver ki live location
    private DriverStatus status; // ONLINE/OFFLINE
    private boolean isBusy; // Ride accepted hai ya nahi

    // Enum to represent driver status
    public enum DriverStatus {
        ONLINE, OFFLINE
    }

    // Constructor
    public Driver(String name, LocationData currentLocation) {
        this.driverId = "123456"; // Temporary ID, later use Firebase UID
        this.name = name;
        this.currentLocation = currentLocation;
        this.status = DriverStatus.ONLINE;
        this.isBusy = false;
    }

    // Getters and Setters (omitted for brevity)
    public String getDriverId() { return driverId; }
    public LocationData getCurrentLocation() { return currentLocation; }
    public DriverStatus getStatus() { return status; }
    public boolean isBusy() { return isBusy; }
    public String getDriverName() { return name; }
    public void setCurrentLocation(LocationData currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
    }
    public void setDriverId(String driverId){
        this.driverId = driverId;
    }

    // ... other getters/setters
}
