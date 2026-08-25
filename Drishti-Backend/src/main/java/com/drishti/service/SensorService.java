package com.drishti.service;

import com.drishti.dto.SensorReadingDto;
import com.drishti.model.SensorReading;
import com.drishti.repository.SensorReadingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SensorService {
    private final SensorReadingRepository repository;
    private final VitalsMonitorService vitalsMonitorService;
    
    public void process(SensorReadingDto dto) {
        SensorReading reading = SensorReading.builder()
            .soldierId(dto.getSoldierId())
            .heading(dto.getHeading())
            .gpsLat(dto.getGpsLat())
            .gpsLon(dto.getGpsLon())
            .heartRate(dto.getHeartRate())
            .temperature(dto.getTemperature())
            .tapEvent(dto.getTapEvent())
            .jawClench(dto.isJawClench())
            .timestamp(LocalDateTime.now())
            .build();
        repository.save(reading);
        vitalsMonitorService.evaluate(dto);
    }
    
    public SensorReadingDto getLatest(String soldierId) {
        SensorReading reading = repository.findFirstBySoldierIdOrderByTimestampDesc(soldierId);
        return reading != null ? SensorReadingDto.fromEntity(reading) : null;
    }
}