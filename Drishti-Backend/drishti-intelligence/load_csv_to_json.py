import json
import random
from datetime import datetime, timedelta

# Your actual data
places_data = [
    ("Kupwara", 34.46, 75.82, 41),
    ("Baramulla", 34.30, 74.47, 95),
    ("Bandipore", 34.35, 74.63, 12),
    ("Ganderbal", 34.45, 75.30, 6),
    ("Budgam", 34.13, 75.13, 28),
    ("Poonch", 33.60, 75.05, 35),
    ("Pulwama", 34.05, 74.80, 72),
    ("Shopian", 34.00, 74.70, 13),
    ("Anantnag", 34.25, 75.35, 73),
    ("Kulgam", 33.95, 75.43, 14),
    ("Rajouri", 32.72, 74.47, 35),
    ("Ramban", 32.93, 75.53, 7),
    ("Kishtwar", 33.78, 76.58, 4),
    ("Reasi", 32.85, 75.93, 5),
    ("Jammu", 32.73, 74.87, 46),
    ("Udhampur", 32.93, 75.15, 40),
    ("Doda", 32.80, 75.93, 69),
    ("Kathua", 32.52, 75.53, 8),
    ("Samba", 32.52, 75.12, 5),
]

# Calculate total incidents
total_incidents = sum(d[3] for d in places_data)

# Generate hotspots with weights
hotspots = [
    (lat, lon, name, round(incidents / total_incidents, 4))
    for name, lat, lon, incidents in places_data
]

def generate_3year_incidents(count=658):
    """Generate 658 incidents based on ACTUAL J&K hotspots"""
    
    incident_types = ["IED", "AMBUSH", "INFILTRATION", "ENCOUNTER", "GRENADE"]
    terrorist_groups = ["LeT", "JeM", "HM", "Al-Badr", "AQ-I", "TRF", "Unknown"]
    location_types = ["Checkpoint", "Convoy", "Highway", "Urban", "Rural"]
    security_forces = ["Army", "CRPF", "Police", "BSF"]
    
    incidents = []
    start_date = datetime(2023, 1, 1)
    end_date = datetime(2026, 6, 28)
    
    for i in range(count):
        base_lat, base_lon, location, frequency = random.choices(
            hotspots, weights=[h[3] for h in hotspots]
        )[0]
        
        lat = base_lat + random.gauss(0, 0.06)
        lon = base_lon + random.gauss(0, 0.06)
        days_diff = (end_date - start_date).days
        date = start_date + timedelta(days=random.randint(0, days_diff))
        
        if random.random() < 0.65:
            total_casualties = 0
            security_casualties = 0
            civilian_casualties = 0
        elif random.random() < 0.80:
            total_casualties = random.randint(1, 2)
            security_casualties = total_casualties
            civilian_casualties = 0
        elif random.random() < 0.95:
            total_casualties = random.randint(3, 5)
            security_casualties = random.randint(1, total_casualties)
            civilian_casualties = total_casualties - security_casualties
        else:
            total_casualties = random.randint(6, 12)
            security_casualties = random.randint(2, total_casualties-1)
            civilian_casualties = total_casualties - security_casualties
        
        incidents.append({
            "id": i + 1,
            "lat": round(lat, 4),
            "lon": round(lon, 4),
            "date": date.strftime("%Y-%m-%d"),
            "time_of_day": random.choice(["EARLY_MORNING", "MORNING", "AFTERNOON", "EVENING", "NIGHT"]),
            "incident_type": random.choices(incident_types, weights=[0.35, 0.25, 0.20, 0.15, 0.05])[0],
            "terrorist_group": random.choice(terrorist_groups),
            "num_militants": random.randint(2, 15),
            "casualties_total": total_casualties,
            "casualties_security": security_casualties,
            "casualties_civilian": civilian_casualties,
            "weapons_seized": random.randint(0, 5),
            "location_type": random.choice(location_types),
            "security_forces_involved": random.choice(security_forces),
            "location_name": location
        })
    
    return incidents

if __name__ == "__main__":
    data = generate_3year_incidents(658)
    with open("incidents_actual_658.json", "w") as f:
        json.dump(data, f, indent=2)
    print(f"✓ Generated {len(data)} incidents (actual distribution)")
    print(f"Hotspots:\n{json.dumps(hotspots, indent=2)}")