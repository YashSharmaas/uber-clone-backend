package com.yrmultimediaco.uber_clone_backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service // Spring ko batata hai ki yeh class business logic handle karegi
public class MapService {

    // 🔥 Key ko application.properties se load karna
    @Value("${google.maps.api.directions-key}")
    private String directionsApiKey;

    private final String GOOGLE_DIRECTIONS_URL = "https://maps.googleapis.com/maps/api/directions/json";
    private final RestTemplate restTemplate = new RestTemplate(); // External calls ke liye tool

    // Direction data fetch karne ka main function
    public String fetchDirections(String origin, String destination) {
        // Full URL construct karna, jismein secret key shamil hai
        String url = GOOGLE_DIRECTIONS_URL +
                "?origin=" + origin +
                "&destination=" + destination +
                "&key=" + directionsApiKey;

        // Server-to-Server call
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody(); // Google se mila JSON sidha wapas bhej do
        } else {
            throw new RuntimeException("Google Directions API failed with status: " + response.getStatusCode() +
                    ". Response: " + response.getBody());
        }
    }
}
