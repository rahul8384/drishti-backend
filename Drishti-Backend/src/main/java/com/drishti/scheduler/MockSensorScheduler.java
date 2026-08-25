package com.drishti.scheduler;

import com.drishti.dto.SensorReadingDto;
import com.drishti.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class MockSensorScheduler {
    private final SensorService sensorService;
    private final Random rnd = new Random();
    private double heading = 0;
    
    @Scheduled(fixedRate = 1000)
    public void generateMockData() {
        List.of("ALPHA", "BRAVO", "CHARLIE", "DELTA").forEach(soldierId -> {
            heading = (heading + rnd.nextDouble() * 3 - 1.5 + 360) % 360;
            
            SensorReadingDto dto = SensorReadingDto.builder()
                .soldierId(soldierId)
                .heading(heading)
                .gpsLat(34.0837 + rnd.nextGaussian() * 0.001)
                .gpsLon(74.7973 + rnd.nextGaussian() * 0.001)
                .heartRate(65 + rnd.nextInt(30))
                .temperature(36.5 + rnd.nextDouble())
                .tapEvent("NONE")
                .jawClench(false)
                .build();
            
            sensorService.process(dto);
        });
    }
}