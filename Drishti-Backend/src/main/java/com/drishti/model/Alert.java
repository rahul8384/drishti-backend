package com.drishti.model;

import com.drishti.dto.AlertType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    private String soldierId;
    
    @Enumerated(EnumType.STRING)
    private AlertType type;
    
    private String message;
    private LocalDateTime timestamp;
}