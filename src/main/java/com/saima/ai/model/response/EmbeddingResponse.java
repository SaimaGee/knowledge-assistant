package com.saima.ai.model.response;

import java.util.List;

public record EmbeddingResponse(
        List<Double> embedding
) {}