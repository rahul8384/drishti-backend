package com.drishti.repository;

import com.drishti.model.SensorReading;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorReadingRepository extends JpaRepository<SensorReading, String> {
    SensorReading findFirstBySoldierIdOrderByTimestampDesc(String soldierId);
}