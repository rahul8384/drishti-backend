package com.drishti.websocket;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WsMessage {
    private String type;
    private Object payload;
    private long timestamp;
    
    public static WsMessage of(String type, Object payload) {
        return new WsMessage(type, payload, System.currentTimeMillis());
    }
}