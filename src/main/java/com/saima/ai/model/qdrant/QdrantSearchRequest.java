package com.saima.ai.model.qdrant;

import java.util.List;

public record QdrantSearchRequest(
        List<Double> vector,
        Integer limit,
        Boolean with_payload
) {}