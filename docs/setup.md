# Local Setup

## Prerequisites

- Java 21
- Maven
- Node.js
- Docker
- Ollama

## Clone Repository

```bash
git clone https://github.com/SaimaGee/knowledge-assistant.git

cd knowledge-assistant
```

## Start Infrastructure

```bash
docker compose -f docker/docker-compose.yml up -d
```

## Pull Models

```bash
docker exec -it ollama ollama pull llama3.2

docker exec -it ollama ollama pull nomic-embed-text
```

## Start Backend

```bash
./mvnw spring-boot:run
```

## Start Frontend

```bash
cd frontend

npm install

npm run dev
```

## URLs

Frontend:

http://localhost:5173

Backend:

http://localhost:8080

Qdrant:

http://localhost:6333

Ollama:

http://localhost:11434