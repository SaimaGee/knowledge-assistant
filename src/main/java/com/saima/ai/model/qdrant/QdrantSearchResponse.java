package com.saima.ai.model.qdrant;

import java.util.List;

public record QdrantSearchResponse(
        List<QdrantHit> result
) {}