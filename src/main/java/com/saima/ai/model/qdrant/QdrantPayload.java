package com.saima.ai.model.qdrant;

public record QdrantPayload(
        String text,
        String documentName,
        Integer chunkNumber
) {}