package com.drishti.service;

import com.drishti.dto.AlertDto;
import com.drishti.model.Alert;
import com.drishti.repository.AlertRepository;
import com.drishti.websocket.WsMessage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AlertService {
    private final AlertRepository repository;
    private final WebSocketPushService pushService;
    
    public void publish(AlertDto alert) {
        Alert entity = Alert.builder()
            .soldierId(alert.getSoldierId())
            .type(alert.getType())
            .message(alert.getMessage())
            .timestamp(LocalDateTime.now())
            .build();
        
        repository.save(entity);
        pushService.pushToAll(WsMessage.of("ALERT", alert));
    }
}