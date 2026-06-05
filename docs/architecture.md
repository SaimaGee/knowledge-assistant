# Architecture

## Overview

AI Knowledge Assistant is a Retrieval Augmented Generation (RAG) application built using:

- React
- Spring Boot
- Ollama
- Qdrant
- Llama 3.2

## High-Level Architecture

```text
+--------------------+
| React Frontend     |
+--------------------+
          |
          v
+--------------------+
| Spring Boot API    |
+--------------------+
          |
    +-----+-----+
    |           |
    v           v
 Ollama      Qdrant
 Llama       Vector DB
```

## Components

### Frontend

Responsible for:

- Uploading PDFs
- Asking questions
- Displaying answers

### Backend

Responsible for:

- Document ingestion
- Text extraction
- Chunking
- Embedding generation
- Retrieval
- Prompt generation

### Ollama

Provides:

- Llama 3.2
- Embedding models

### Qdrant

Stores:

- Text chunks
- Embeddings
- Metadata
