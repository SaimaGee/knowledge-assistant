package com.saima.ai.model.qdrant;

import java.util.List;

public record QdrantPoint(
        String       id,
        List<Double> vector,
        PointPayload payload) {}