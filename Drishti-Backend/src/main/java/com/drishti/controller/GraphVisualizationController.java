package com.drishti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.neo4j.driver.Session;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;

@RestController
@RequestMapping("/api/v1/intel")
public class GraphVisualizationController {

    @Autowired
    private Driver neo4jDriver;

    @GetMapping("/graph")
    public Map<String, Object> getGraphData() {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> nodes = new ArrayList<>();
        List<Map<String, Object>> links = new ArrayList<>();
        
        try (Session session = neo4jDriver.session()) {
            // First, get all incidents
            Result incidentResult = session.run("""
                MATCH (i:Incident)
                RETURN 
                    i.id as incident_id, 
                    i.location as location,
                    i.type as incident_type,
                    i.date as date,
                    i.casualties as casualties
                LIMIT 50
            """);
            
            Set<String> nodeIds = new HashSet<>();
            
            while (incidentResult.hasNext()) {
                Record record = incidentResult.next();
                String incidentId = "INC_" + record.get("incident_id").asInt();
                
                if (nodeIds.add(incidentId)) {
                    nodes.add(Map.of(
                        "id", incidentId,
                        "label", incidentId.substring(0, 15),
                        "type", "incident",
                        "color", "#FF6B6B", // Red
                        "size", 30,
                        "metadata", Map.of(
                            "type", record.get("incident_type").asString(),
                            "location", record.get("location").asString(),
                            "date", record.get("date").asString(),
                            "casualties", record.get("casualties").asInt()
                        )
                    ));
                }
            }
            
            // Get relationships between incidents
            Result relResult = session.run("""
                MATCH (a:Incident)-[r:FOLLOWED_BY]->(b:Incident)
                RETURN 
                    a.id as source_id,
                    b.id as target_id
                LIMIT 100
            """);
            
            while (relResult.hasNext()) {
                Record record = relResult.next();
                String sourceId = "INC_" + record.get("source_id").asInt();
                String targetId = "INC_" + record.get("target_id").asInt();
                
                links.add(Map.of(
                    "source", sourceId,
                    "target", targetId,
                    "label", "FOLLOWED_BY"
                ));
            }
            
            System.out.println("✓ Loaded " + nodes.size() + " nodes, " + links.size() + " links");
            
        } catch (Exception e) {
            System.err.println("Neo4j Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        response.put("nodes", nodes);
        response.put("links", links);
        return response;
    }
}