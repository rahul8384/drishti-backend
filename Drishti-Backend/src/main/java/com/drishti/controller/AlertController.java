package com.drishti.controller;

import com.drishti.dto.AlertDto;
import com.drishti.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/alerts")
@RequiredArgsConstructor
public class AlertController {
    private final AlertService alertService;
    
    @PostMapping
    public ResponseEntity<Void> publishAlert(@RequestBody AlertDto alert) {
        alertService.publish(alert);
        return ResponseEntity.ok().build();
    }
}