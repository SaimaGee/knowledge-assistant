# AI Knowledge Assistant

Enterprise-grade AI Knowledge Assistant that enables users to upload documents and ask natural language questions using Retrieval Augmented Generation (RAG).

Built using Java Spring Boot, React, Ollama/Llama, Qdrant Vector Database and Docker.

---

## Features

### AI Chat

- Conversational interface
- Context-aware responses
- Multi-turn conversations

### Document Management

- Upload PDF documents
- Extract text automatically
- Chunk and index content

### RAG Pipeline

- Semantic search
- Vector embeddings
- Context retrieval
- Source citations

### LLM Provider Abstraction

Supports:

- Ollama (Local Llama)
- Amazon Bedrock
- Anthropic Claude
- OpenAI

### Enterprise Features

- REST APIs
- Docker deployment
- Authentication
- Audit logging
- CI/CD ready

---

## Architecture

```text
React UI
    |
Spring Boot API
    |
+------------------+
|                  |
Ollama      Amazon Bedrock
Llama       Claude Sonnet
    |
Embedding Service
    |
Qdrant
    |
Knowledge Base
```

---

## Technology Stack

### Frontend

- React
- TypeScript
- Material UI

### Backend

- Java 21
- Spring Boot 3
- Maven

### AI

- Ollama
- Llama 3.2
- LangChain4j

### Search

- Qdrant

### Infrastructure

- Docker
- AWS

---

## Getting Started

### Prerequisites

- Java 21
- Maven
- Docker
- Ollama

### Install Ollama

```bash
ollama pull llama3.2
