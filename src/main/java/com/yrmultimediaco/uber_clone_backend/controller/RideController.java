package com.yrmultimediaco.uber_clone_backend.controller;

import com.yrmultimediaco.uber_clone_backend.model.DirectionsRequest;
import com.yrmultimediaco.uber_clone_backend.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    private final MapService mapService;

    // Dependency Injection: Spring automatically MapService object dega
    @Autowired
    public RideController(MapService mapService) {
        this.mapService = mapService;
    }

    // Endpoint: POST request /api/rides/route par aayegi
    @PostMapping("/route")
    public ResponseEntity<String> getRoute(@RequestBody DirectionsRequest request) {
        // Simple validation
        if (request.getOrigin() == null || request.getDestination() == null) {
            return ResponseEntity.badRequest().body("Origin and Destination are required.");
        }

        try {
            // Service ko call karo jo Google ko call karega
            String googleResponse = mapService.fetchDirections(request.getOrigin(), request.getDestination());

            // Final response Android app ko wapas bhej dena
            return ResponseEntity.ok(googleResponse);

        } catch (Exception e) {
            // Agar Google ya server mein error aaya
            return ResponseEntity.internalServerError().body("Error fetching route: " + e.getMessage());
        }
    }
}
