package com.saima.ai.model.request;

import com.saima.ai.model.qdrant.VectorParams;

public record CreateCollectionRequest(VectorParams vectors) {}