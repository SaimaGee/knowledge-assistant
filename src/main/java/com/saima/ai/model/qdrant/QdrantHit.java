package com.saima.ai.model.qdrant;

public record QdrantHit(
        Integer id,
        Double score,
        QdrantPayload payload
) {}