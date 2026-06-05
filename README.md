# AI Knowledge Assistant

Enterprise-grade AI Knowledge Assistant built using Java Spring Boot, React, Ollama, Llama 3.2, Qdrant and Retrieval Augmented Generation (RAG).

The application enables users to upload PDF documents and ask natural language questions. Relevant document sections are retrieved using vector search and provided to the LLM to generate accurate, context-aware answers.

---

## Features

### AI Chat

* Conversational question answering
* Context-aware responses
* Local LLM support using Ollama
* Extensible architecture for Bedrock, Claude and OpenAI

### Document Processing

* PDF upload
* Text extraction using Apache PDFBox
* Document storage
* Metadata management

### Retrieval Augmented Generation (RAG)

* Text chunking
* Embedding generation
* Vector similarity search
* Context retrieval
* Source-aware responses

### Vector Database

* Qdrant integration
* Semantic search
* High-performance vector indexing

### Full Stack Architecture

* React frontend
* Spring Boot backend
* Ollama LLM runtime
* Qdrant vector database

---

## Architecture

```text
                    React Frontend
                           |
                           v
                 +------------------+
                 | Spring Boot API  |
                 +------------------+
                           |
            +--------------+--------------+
            |                             |
            v                             v
    +----------------+        +--------------------+
    | Ollama         |        | Qdrant             |
    | Llama 3.2      |        | Vector Database    |
    | Embeddings     |        +--------------------+
    +----------------+
            |
            v
      RAG Pipeline
            |
            v
      Knowledge Base
```

---

## RAG Workflow

### Document Ingestion

```text
PDF Upload
     |
     v
Extract Text
     |
     v
Chunk Text
     |
     v
Generate Embeddings
     |
     v
Store in Qdrant
```

### Question Answering

```text
User Question
      |
      v
Generate Embedding
      |
      v
Search Qdrant
      |
      v
Retrieve Relevant Chunks
      |
      v
Build Prompt
      |
      v
Llama 3.2
      |
      v
Generate Answer
```

---

## Technology Stack

### Frontend

* React
* Vite
* JavaScript
* Material UI (planned)

### Backend

* Java 21
* Spring Boot 3
* Maven

### AI & RAG

* Ollama
* Llama 3.2
* nomic-embed-text
* Qdrant

### Document Processing

* Apache PDFBox

### Infrastructure

* Docker
* Docker Compose

---

## Project Structure

```text
knowledge-assistant/
│
├── frontend/
│   ├── src/
│   ├── public/
│   └── package.json
│
├── src/main/java/com/saima/ai/
│   ├── controller/
│   ├── service/
│   ├── model/
│   │   ├── request/
│   │   ├── response/
│   │   └── qdrant/
│   └── config/
│
├── uploads/
│   ├── pdfs/
│   └── extracted/
│
├── docker/
│   └── docker-compose.yml
│
├── docs/
│   ├── architecture.md
│   ├── setup.md
│   ├── api.md
│   ├── rag-flow.md
│   ├── deployment.md
│   └── roadmap.md
│
├── README.md
└── pom.xml
```

---

## Getting Started

### Prerequisites

* Java 21
* Maven
* Node.js
* Docker
* Git

---

### Clone Repository

```bash
git clone https://github.com/SaimaGee/knowledge-assistant.git

cd knowledge-assistant
```

---

### Start Infrastructure

```bash
docker compose -f docker/docker-compose.yml up -d
```

---

### Pull Ollama Models

```bash
docker exec -it ollama ollama pull llama3.2

docker exec -it ollama ollama pull nomic-embed-text
```

---

### Run Backend

```bash
./mvnw spring-boot:run
```

---

### Run Frontend

```bash
cd frontend

npm install

npm run dev
```

---

## API Endpoints

### Upload PDF

```http
POST /api/documents/upload
```

Example:

```bash
curl \
-F "file=@sample.pdf" \
http://localhost:8080/api/documents/upload
```

---

### Ask Question

```http
POST /api/chat
```

Request:

```json
{
  "message": "What is AWS IAM?"
}
```

Response:

```json
{
  "answer": "AWS IAM is a service used to manage users, roles and permissions."
}
```

---

## Documentation

Additional project documentation can be found in the `docs` folder:

* Architecture
* Setup Guide
* API Reference
* RAG Workflow
* Deployment Guide
* Roadmap

---

## Roadmap

### Phase 1

* Spring Boot API
* Ollama Integration
* LLM Chat Service

### Phase 2

* PDF Upload
* Text Extraction
* Document Storage

### Phase 3

* Chunking Service
* Embedding Service
* Qdrant Integration
* RAG Pipeline

### Phase 4

* React User Interface
* Chat History
* Document Library
* Source Citations

### Phase 5

* Authentication
* Multi-user Support
* Dockerized Deployment
* AWS Hosting

---

## Future Enhancements

* LangChain4j Integration
* Amazon Bedrock Support
* Claude Sonnet Support
* OpenAI Support
* Streaming Responses
* OCR Processing
* Multi-document Search
* Document Summaries

---

## Author

**Saima Shahid**

Senior Software Engineer

Specialising in Java, Spring Boot, React, Cloud Platforms and AI-powered applications.
