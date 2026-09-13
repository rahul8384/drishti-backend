# Drishti – Wearable Battlefield Intelligence Platform

> *Drishti (दृष्टि) – Hindi for "vision/sight"*

AI-powered battlefield intelligence platform with graph-based tactical modeling and real-time visualization.

## Current Status

**Proof of Concept - Active Development**

Demonstrated end-to-end communication from simulated sensor inputs through AI inference to interactive HUD visualization.

## What's Built

✅ **Spring Boot REST APIs** – Backend services for tactical intelligence data exposure  
✅ **Neo4j Knowledge Graph** – Graph database with J&K incident datasets modeling military relationships  
✅ **GNN Model** – Graph Neural Network for tactical pattern prediction  
✅ **React HUD Prototype** – Interactive visualization of battlefield intelligence  
✅ **Redis Caching** – Optimized data access layer  
✅ **Docker Compose** – Containerized local development environment  

## Tech Stack

- **Backend:** Java 17, Spring Boot 3, Neo4j, PostgreSQL, Redis
- **Frontend:** React
- **DevOps:** Docker Compose

## Getting Started

### Prerequisites
- Java 17+
- Node.js 18+
- Docker & Docker Compose

### Setup

1. **Clone repository:**
```bash
   git clone https://github.com/rahul8384/drishti-backend.git
   cd drishti
```

2. **Configure environment:**
```bash
   cp docker-compose.example.yml docker-compose.yml
   cp application.properties.example application.properties
   cp .env.example .env
```

3. **Update credentials in:**
   - `docker-compose.yml`
   - `application.properties`
   - `.env`

4. **Start services:**
```bash
   docker-compose up -d
```

5. **Run backend:**
```bash
   cd backend
   ./mvnw spring-boot:run
```
   API: `http://localhost:8080`

6. **Run frontend:**
```bash
   cd frontend
   npm install
   npm start
```
   UI: `http://localhost:3000`

## Services

| Service | Port | Credentials |
|---------|------|-------------|
| Spring Boot API | 8080 | - |
| Neo4j Browser | 7474 | `.env` |
| PostgreSQL | 5432 | `.env` |
| Redis | 6379 | None |

## Project Structure

### Key Directories

- **config/** – Spring Boot configuration & bean definitions
- **controller/** – REST API endpoints
- **service/** – Business logic & GNN inference pipeline
- **repository/** – Neo4j & PostgreSQL data access
- **model/** – Entity classes
- **dto/** – Data Transfer Objects
- **websocket/** – Real-time HUD communication handlers
- **scheduler/** – Background scheduled tasks

## Architecture

Simulate Sensor
   ->
Spring Boot REST APIs
   ->
GNN Inference
   ->
Neo4j, PostgreSQL, Redis
   ->
React HUD


## Development

### Run backend
```bash
cd backend
./mvnw spring-boot:run
```

### Run frontend
```bash
cd frontend
npm start
```

### Stop services
```bash
docker-compose down
```

## Security

- Configuration stored in environment variables (`.env`)
- Never commit `application.properties`, `docker-compose.yml`, or `.env`
- JWT-protected API endpoints

## Author

Rahul  
Java Backend Developer | Delhi NCR, India

---

Built as a personal project for tactical intelligence systems
