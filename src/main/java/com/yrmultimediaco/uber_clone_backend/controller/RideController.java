package com.yrmultimediaco.uber_clone_backend.controller;

import com.yrmultimediaco.uber_clone_backend.data.AcceptRideRequest;
import com.yrmultimediaco.uber_clone_backend.data.DriverLocationRequest;
import com.yrmultimediaco.uber_clone_backend.data.LocationData;
import com.yrmultimediaco.uber_clone_backend.data.RideRequest;
import com.yrmultimediaco.uber_clone_backend.model.DirectionsRequest;
import com.yrmultimediaco.uber_clone_backend.repository.DriverRepository;
import com.yrmultimediaco.uber_clone_backend.service.MapService;
import com.yrmultimediaco.uber_clone_backend.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    private final MapService mapService;
    private final RideService rideService;
    private final DriverRepository driverRepository; // 👈 1. Repository declare karein
    private final SimpMessagingTemplate messagingTemplate;

    // 👈 2. Constructor mein teenon dependencies inject karein
    @Autowired
    public RideController(MapService mapService, RideService rideService, DriverRepository driverRepository, SimpMessagingTemplate messagingTemplate) {
        this.mapService = mapService;
        this.rideService = rideService;
        this.driverRepository = driverRepository;
        this.messagingTemplate  = messagingTemplate;
    }

    // Endpoint 1: Directions fetch karne ke liye (Rider App)
    @PostMapping("/route")
    public ResponseEntity<String> getRoute(@RequestBody DirectionsRequest request) {
        if (request.getOrigin() == null || request.getDestination() == null) {
            return ResponseEntity.badRequest().body("Origin and Destination are required.");
        }
        try {
            String googleResponse = mapService.fetchDirections(request.getOrigin(), request.getDestination());
            return ResponseEntity.ok(googleResponse);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error fetching route: " + e.getMessage());
        }
    }

    // Endpoint 2: Ride dispatch/request karne ke liye (Rider App)
    @PostMapping("/request")
    public ResponseEntity<String> requestRide(@RequestBody RideRequest request) {
        System.out.println("🔥 RECEIVING RIDER REQUEST AT CONTROLLER: " + request.getOrigin());

        boolean isDispatched = rideService.dispatchRide(request);
        if (isDispatched) {
            return ResponseEntity.ok("Ride dispatched successfully to nearby drivers.");
        } else {
            return ResponseEntity.badRequest().body("No drivers available nearby.");
        }
    }

    // Endpoint 3: Driver ki live location update karne ke liye (Driver App)
    @PostMapping("/update-location")
    public ResponseEntity<Void> updateDriverLocation(@RequestBody DriverLocationRequest request) {
        // DriverRepository ka method call karein
        driverRepository.updateDriverLocation(
                request.getDriverId(),
                new LocationData(request.getLat(), request.getLng())
        );

        String locationPayload = "{\"type\":\"LOCATION_UPDATE\", \"lat\":" + request.getLat() + ", \"lng\":" + request.getLng() + "}";
        messagingTemplate.convertAndSend("/topic/rider/" + request.getRiderId(), locationPayload);

        return ResponseEntity.ok().build();
    }

    // ✅ Naya Endpoint: Driver Acceptance handle karne ke liye
    @PostMapping("/accept")
    public ResponseEntity<String> acceptRide(@RequestBody AcceptRideRequest acceptRequest) {
        if (acceptRequest.getRiderId() == null || acceptRequest.getRiderId().isEmpty()) {
            System.out.println("❌ ERROR: RiderId is NULL in Accept Request!");
            return ResponseEntity.badRequest().body("RiderId is missing");
        }

        System.out.println("✅ ACCEPT_SIGNAL: Driver " + acceptRequest.getDriverId() +
                " for Ride " + acceptRequest.getRideId());

        // 🎯 Rider ko notify karein: /topic/rider/{riderId}
        String riderTopic = "/topic/rider/" + acceptRequest.getRiderId();

        // 🎯 Ab hum ek JSON bhejenge sirf ek string nahi
        String driverData = "{" +
                "\"status\":\"DRIVER_FOUND_SUCCESS\"," +
                "\"driverName\":\"Rahul Kumar\"," +
                "\"vehicle\":\"UP-78-AB-1234\"," +
                "\"phone\":\"+91-9876543210\"" +
                "}";

        // Signal bhej rahe hain: "DRIVER_FOUND_SUCCESS"
        messagingTemplate.convertAndSend(riderTopic, driverData);

        System.out.println("🚀 SIGNAL_SENT: Notified Rider at topic: " + riderTopic);

        return ResponseEntity.ok("Rider notified of acceptance.");
    }
}