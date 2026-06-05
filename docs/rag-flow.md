# RAG Flow

## Document Ingestion

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

## Question Answering

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
Retrieve Chunks
       |
       v
Build Prompt
       |
       v
Llama
       |
       v
Answer
```

## Benefits

- Faster responses
- Reduced token usage
- Better accuracy
- Document-grounded answers
