package com.saima.ai.model.qdrant;

import java.util.List;

public record UpsertPointsRequest(List<QdrantPoint> points) {}