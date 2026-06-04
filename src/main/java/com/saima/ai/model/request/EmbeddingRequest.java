package com.saima.ai.model.request;

public record EmbeddingRequest(
        String model,
        String prompt
) {}