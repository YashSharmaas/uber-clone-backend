package com.yrmultimediaco.uber_clone_backend.service;

import com.yrmultimediaco.uber_clone_backend.data.LocationData;
import com.yrmultimediaco.uber_clone_backend.data.RideRequest;
import com.yrmultimediaco.uber_clone_backend.model.Driver;
import com.yrmultimediaco.uber_clone_backend.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RideService {

    private final DriverRepository driverRepository;
    private final SimpMessagingTemplate messagingTemplate;

    // Testing ke liye range bohot badi rakhi hai
    private static final double MAX_DISPATCH_RANGE_KM = 20000.0; 

    @Autowired
    public RideService(DriverRepository driverRepository, SimpMessagingTemplate messagingTemplate) {
        this.driverRepository = driverRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public boolean dispatchRide(RideRequest request) {
        System.out.println("📩 RIDE_REQUEST_START: From " + request.getOrigin());

        LocationData riderOrigin = new LocationData(
                Double.parseDouble(request.getOrigin().split(",")[0]),
                Double.parseDouble(request.getOrigin().split(",")[1])
        );

        List<Driver> allDrivers = driverRepository.findAllActive();
        System.out.println("📊 SYSTEM_CHECK: Total Active Drivers in Repo = " + allDrivers.size());

        List<Driver> nearbyDrivers = allDrivers.stream()
                .filter(driver -> driver.getStatus() == Driver.DriverStatus.ONLINE && !driver.isBusy())
                .collect(Collectors.toList());

        System.out.println("🔍 MATCHING_CHECK: Nearby available drivers = " + nearbyDrivers.size());

        if (nearbyDrivers.isEmpty()) {
            System.out.println("❌ DISPATCH_FAILED: No active drivers found.");
            return false;
        }

        // Sabse pehle milne wale driver ko pick karein
        Driver selectedDriver = nearbyDrivers.get(0);
        String driverId = selectedDriver.getDriverId();
        String topic = "/topic/driver/" + driverId;

        // 🔥 Push the WebSocket message
        messagingTemplate.convertAndSend(topic, request);

        System.out.println("✅ DISPATCH_SUCCESS: Message sent to Topic -> " + topic);
        return true;
    }
}