package com.drishti.service;

import com.drishti.dto.AlertDto;
import com.drishti.dto.AlertType;
import com.drishti.dto.SensorReadingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VitalsMonitorService {
    private final AlertService alertService;
    
    @Value("${vitals.hr.max:160}")
    private int hrMax;
    
    @Value("${vitals.hr.min:40}")
    private int hrMin;
    
    @Value("${vitals.temp.max:39.5}")
    private double tempMax;
    
    public void evaluate(SensorReadingDto reading) {
        if (reading.getHeartRate() > hrMax) {
            alertService.publish(AlertDto.of(reading.getSoldierId(), AlertType.VITALS_HR_HIGH,
                "HR critical: " + reading.getHeartRate() + " BPM"));
        }
        if (reading.getHeartRate() < hrMin && reading.getHeartRate() > 0) {
            alertService.publish(AlertDto.of(reading.getSoldierId(), AlertType.VITALS_HR_LOW,
                "HR low: " + reading.getHeartRate() + " BPM"));
        }
        if (reading.getTemperature() > tempMax) {
            alertService.publish(AlertDto.of(reading.getSoldierId(), AlertType.VITALS_TEMP_HIGH,
                "Temp critical: " + reading.getTemperature() + "°C"));
        }
    }
}