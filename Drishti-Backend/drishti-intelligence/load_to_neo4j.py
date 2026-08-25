import json
from neo4j import GraphDatabase

# Connect to your local Neo4j (from docker-compose)
driver = GraphDatabase.driver("bolt://localhost:7687", auth=("neo4j", "drishti123"))

def load_incidents(json_file):
    with open(json_file) as f:
        incidents = json.load(f)
    
    with driver.session() as session:
        # Clear old data
        session.run("MATCH (n) DETACH DELETE n")
        
        # Create nodes
        for inc in incidents:
            session.run("""
                CREATE (i:Incident {
                    id: $id, lat: $lat, lon: $lon, date: $date,
                    type: $type, casualties: $casualties, location: $location,
                    group: $group, militants: $militants, weapons: $weapons
                })
            """, id=inc["id"], lat=inc["lat"], lon=inc["lon"], 
                date=inc["date"], type=inc["incident_type"], 
                casualties=inc["casualties_total"], location=inc["location_name"],
                group=inc["terrorist_group"], militants=inc["num_militants"],
                weapons=inc["weapons_seized"])
        
        # Create temporal relationships (incident chains)
        session.run("""
            MATCH (a:Incident), (b:Incident)
            WHERE a.date < b.date AND 
                  point.distance(point({latitude: a.lat, longitude: a.lon}),
                           point({latitude: b.lat, longitude: b.lon})) < 50000
            CREATE (a)-[:FOLLOWED_BY]->(b)
        """)
    
    print(f"Loaded {len(incidents)} incidents. Relationships created.")
    driver.close()

if __name__ == "__main__":
    load_incidents("incidents_actual_658.json")