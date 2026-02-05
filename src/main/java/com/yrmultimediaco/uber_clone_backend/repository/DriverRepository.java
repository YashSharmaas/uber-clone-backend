package com.yrmultimediaco.uber_clone_backend.repository;

import com.yrmultimediaco.uber_clone_backend.data.LocationData;
import com.yrmultimediaco.uber_clone_backend.model.Driver;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class DriverRepository {

    private final List<Driver> activeDrivers = new CopyOnWriteArrayList<>();

    public DriverRepository() {
        // Mock data hata diya hai taaki testing clean ho
    }

    public List<Driver> findAllActive() {
        return activeDrivers;
    }

    public void updateDriverLocation(String driverId, LocationData newLocation) {
        Optional<Driver> existingDriver = activeDrivers.stream()
                .filter(d -> d.getDriverId().equals(driverId))
                .findFirst();

        if (existingDriver.isPresent()) {
            existingDriver.get().setCurrentLocation(newLocation);
            System.out.println("📍 DRIVER_FOUND: ID " + driverId + " updated location.");
        } else {
            // Naye driver ko register karein
            Driver newDriver = new Driver("Real Driver", newLocation);
            newDriver.setDriverId(driverId);
            activeDrivers.add(newDriver);
            System.out.println("✨ NEW_DRIVER_REGISTERED: ID " + driverId);
        }
    }
}