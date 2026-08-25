package com.drishti.dto;

import com.drishti.model.SensorReading;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorReadingDto {
    private String soldierId;
    private double heading;
    private double gpsLat;
    private double gpsLon;
    private int heartRate;
    private double temperature;
    private String tapEvent;
    private boolean jawClench;
    
    public static SensorReadingDto fromEntity(SensorReading entity) {
        return SensorReadingDto.builder()
            .soldierId(entity.getSoldierId())
            .heading(entity.getHeading())
            .gpsLat(entity.getGpsLat())
            .gpsLon(entity.getGpsLon())
            .heartRate(entity.getHeartRate())
            .temperature(entity.getTemperature())
            .tapEvent(entity.getTapEvent())
            .jawClench(entity.isJawClench())
            .build();
    }
}