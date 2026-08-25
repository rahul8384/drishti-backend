package com.drishti.service;

import com.drishti.websocket.WsMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketPushService {
    private final SimpMessagingTemplate messaging;
    
    public void pushToAll(WsMessage message) {
        messaging.convertAndSend("/topic/hud", message);
    }
    
    public void pushToSoldier(String soldierId, WsMessage message) {
        messaging.convertAndSendToUser(soldierId, "/topic/hud", message);
    }
}