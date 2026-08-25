package com.drishti.controller;

import com.drishti.dto.SensorReadingDto;
import com.drishti.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sensors")
@RequiredArgsConstructor
public class SensorController {
    private final SensorService sensorService;
    
    @PostMapping("/reading")
    public ResponseEntity<Void> ingestReading(@RequestBody SensorReadingDto dto) {
        sensorService.process(dto);
        return ResponseEntity.accepted().build();
    }
    
    @GetMapping("/{soldierId}/latest")
    public ResponseEntity<SensorReadingDto> getLatest(@PathVariable String soldierId) {
        return ResponseEntity.ok(sensorService.getLatest(soldierId));
    }
}