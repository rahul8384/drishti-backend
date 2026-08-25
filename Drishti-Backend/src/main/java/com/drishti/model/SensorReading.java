package com.drishti.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorReading {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	 	private String soldierId;
	    private double heading;
	    private double gpsLat;
	    private double gpsLon;
	    private int heartRate;
	    private double temperature;
	    private String tapEvent;
	    private boolean jawClench;
	    private LocalDateTime timestamp;
}
