import requests
import re
import json
import time

SEED_INCIDENTS = []

def build_seed_dataset():
    incidents = [
        {"id": 1, "lat": 34.0837, "lon": 74.7973, "date": "2023-04-15", "type": "IED", "casualties": 2, "location_name": "Srinagar"},
        {"id": 2, "lat": 33.7782, "lon": 76.5762, "date": "2023-06-20", "type": "AMBUSH", "casualties": 4, "location_name": "Kishtwar"},
        {"id": 3, "lat": 34.2996, "lon": 74.4731, "date": "2023-08-10", "type": "IED", "casualties": 1, "location_name": "Baramulla"},
        {"id": 4, "lat": 33.6, "lon": 75.05, "date": "2024-01-12", "type": "INFILTRATION", "casualties": 0, "location_name": "Poonch"},
        {"id": 5, "lat": 34.05, "lon": 74.8, "date": "2024-03-22", "type": "IED", "casualties": 3, "location_name": "Pulwama"},
    ]
    return incidents

if __name__ == "__main__":
    data = build_seed_dataset()
    with open("incidents.json", "w") as f:
        json.dump(data, f, indent=2)
    print(f"Saved {len(data)} incidents to incidents.json")