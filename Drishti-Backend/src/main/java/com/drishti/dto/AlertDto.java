package com.drishti.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertDto {
    private String id;
    private String soldierId;
    private AlertType type;
    private String message;
    private long timestamp;
    
    public static AlertDto of(String soldierId, AlertType type, String message) {
        return AlertDto.builder()
            .soldierId(soldierId)
            .type(type)
            .message(message)
            .timestamp(System.currentTimeMillis())
            .build();
    }
}