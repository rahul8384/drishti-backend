package com.drishti.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@RestController
@RequestMapping("/api/v1/intel")
public class ThreatIntelligenceController {
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    private RestTemplate restTemplate = new RestTemplate();
    
    @GetMapping("/risk-predict")
    public ResponseEntity<Map> predictRisk(@RequestParam double lat, 
                                           @RequestParam double lon,
                                           @RequestParam int casualties) {
        try {
            Map payload = Map.of("lat", lat, "lon", lon, "casualties", casualties);
            Map response = restTemplate.postForObject(
                "http://localhost:5001/predict", 
                payload, 
                Map.class
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping("/heatmap")
    public ResponseEntity<List<Map>> getHeatmap() {
        List<Map> zones = List.of(
            Map.of("lat", 34.46, "lon", 75.82, "risk", 1.8, "label", "Kupwara"),
            Map.of("lat", 34.30, "lon", 74.47, "risk", 3.2, "label", "Baramulla"),
            Map.of("lat", 34.35, "lon", 74.63, "risk", 0.9, "label", "Bandipore"),
            Map.of("lat", 34.45, "lon", 75.30, "risk", 0.4, "label", "Ganderbal"),
            Map.of("lat", 34.13, "lon", 75.13, "risk", 1.2, "label", "Budgam"),
            Map.of("lat", 33.60, "lon", 75.05, "risk", 1.5, "label", "Poonch"),
            Map.of("lat", 34.05, "lon", 74.80, "risk", 2.4, "label", "Pulwama"),
            Map.of("lat", 34.00, "lon", 74.70, "risk", 0.9, "label", "Shopian"),
            Map.of("lat", 34.25, "lon", 75.35, "risk", 2.6, "label", "Anantnag"),
            Map.of("lat", 33.95, "lon", 75.43, "risk", 1.0, "label", "Kulgam"),
            Map.of("lat", 32.72, "lon", 74.47, "risk", 1.5, "label", "Rajouri"),
            Map.of("lat", 32.93, "lon", 75.53, "risk", 0.5, "label", "Ramban"),
            Map.of("lat", 33.78, "lon", 76.58, "risk", 0.3, "label", "Kishtwar"),
            Map.of("lat", 32.85, "lon", 75.93, "risk", 0.4, "label", "Reasi"),
            Map.of("lat", 32.73, "lon", 74.87, "risk", 2.0, "label", "Jammu"),
            Map.of("lat", 32.93, "lon", 75.15, "risk", 1.8, "label", "Udhampur"),
            Map.of("lat", 32.80, "lon", 75.93, "risk", 2.3, "label", "Doda"),
            Map.of("lat", 32.52, "lon", 75.53, "risk", 0.6, "label", "Kathua"),
            Map.of("lat", 32.52, "lon", 75.12, "risk", 0.3, "label", "Samba")
        );
        return ResponseEntity.ok(zones);
    }
}