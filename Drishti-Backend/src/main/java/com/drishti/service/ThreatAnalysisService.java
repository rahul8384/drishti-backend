package com.drishti.service;

import com.drishti.dto.AlertDto;
import com.drishti.dto.AlertType;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ThreatAnalysisService {
    private final AlertService alertService;
    private final RestTemplate restTemplate;
    private final Random rnd = new Random();
    
    private final List<Map<String, Object>> zones = List.of(
        Map.of("lat", 34.46, "lon", 75.82, "label", "Kupwara"),
        Map.of("lat", 34.30, "lon", 74.47, "label", "Baramulla"),
        Map.of("lat", 34.35, "lon", 74.63, "label", "Bandipore"),
        Map.of("lat", 34.45, "lon", 75.30, "label", "Ganderbal"),
        Map.of("lat", 34.13, "lon", 75.13, "label", "Budgam"),
        Map.of("lat", 33.60, "lon", 75.05, "label", "Poonch"),
        Map.of("lat", 34.05, "lon", 74.80, "label", "Pulwama"),
        Map.of("lat", 34.00, "lon", 74.70, "label", "Shopian"),
        Map.of("lat", 34.25, "lon", 75.35, "label", "Anantnag"),
        Map.of("lat", 33.95, "lon", 75.43, "label", "Kulgam"),
        Map.of("lat", 32.72, "lon", 74.47, "label", "Rajouri"),
        Map.of("lat", 32.93, "lon", 75.53, "label", "Ramban"),
        Map.of("lat", 33.78, "lon", 76.58, "label", "Kishtwar"),
        Map.of("lat", 32.85, "lon", 75.93, "label", "Reasi"),
        Map.of("lat", 32.73, "lon", 74.87, "label", "Jammu"),
        Map.of("lat", 32.93, "lon", 75.15, "label", "Udhampur"),
        Map.of("lat", 32.80, "lon", 75.93, "label", "Doda"),
        Map.of("lat", 32.52, "lon", 75.53, "label", "Kathua"),
        Map.of("lat", 32.52, "lon", 75.12, "label", "Samba")
    );
    
    @Scheduled(fixedRate = 25000)
    public void scanForThreats() {
        System.out.println("🔍 Scanning...");
        for (Map<String, Object> zone : zones) {
            try {
                double lat = (Double) zone.get("lat");
                double lon = (Double) zone.get("lon");
                String label = (String) zone.get("label");

                Map response = restTemplate.postForObject(
                    "http://localhost:5001/predict",
                    Map.of("lat", lat, "lon", lon, "casualties", rnd.nextInt(5)),
                    Map.class
                );
                
                System.out.println("  " + label + " → " + response);
                
                String threatLevel = (String) response.get("threat_level");
                double risk = response.containsKey("risk") ? ((Number) response.get("risk")).doubleValue() : 0;
                
                System.out.println("    Threat: " + threatLevel + ", Risk: " + risk);
                
                if ("HIGH".equals(threatLevel) || "MEDIUM".equals(threatLevel)) {
                    System.out.println("    ⚠️ PUBLISHING ALERT FOR: " + label);
                    alertService.publish(AlertDto.of("INTEL", AlertType.THREAT_DETECTED,
                        "⚠️ THREAT: " + label + " [" + threatLevel + "]"));
                }
            } catch (Exception e) {
                System.err.println("Error scanning " + zone.get("label") + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}